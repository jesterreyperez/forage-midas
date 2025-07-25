// Declares this file is part of the "com.jpmc.midascore" package
package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction; // The transaction object coming from Kafka
import com.jpmc.midascore.entity.TransactionRecord; // Entity to save transaction data to database
import com.jpmc.midascore.entity.UserRecord; // Entity representing a user
import com.jpmc.midascore.model.Incentive; // Incentive API response object

import com.jpmc.midascore.repository.TransactionRecordRepository; // Used to save transactions
import com.jpmc.midascore.repository.UserRepository; // Used to fetch/save user data

import org.springframework.beans.factory.annotation.Autowired; // Lets Spring inject dependencies
import org.springframework.beans.factory.annotation.Value; // Lets us read values from application.yml
import org.springframework.http.ResponseEntity; // Represents the full HTTP response
import org.springframework.kafka.annotation.KafkaListener; // Runs method when message is received from Kafka
import org.springframework.stereotype.Component; // Tells Spring this class is a managed component
import org.springframework.web.client.RestTemplate; // Helps us make HTTP requests

@Component // Marks this class so Spring can detect and use it automatically
public class TransactionListener {

    @Value("${general.kafka-topic}") // Reads the Kafka topic name from application.yml
    private String topic;

    @Autowired // Injects the UserRepository to access users in the database
    private UserRepository userRepository;

    @Autowired // Injects the TransactionRecordRepository to save transactions
    private TransactionRecordRepository transactionRecordRepository;

    @Autowired // Injects the RestTemplate to call the external Incentive API
    private RestTemplate restTemplate;

    // This method is triggered automatically when a new message arrives on the Kafka topic
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    public void listen(Transaction transaction) {
        // Get sender and recipient from the database using their IDs
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // If either sender or recipient does not exist, exit early
        if (sender == null || recipient == null) {
            return;
        }

        // If the sender doesn’t have enough balance, cancel the transaction
        if (sender.getBalance() < transaction.getAmount()) {
            return;
        }

        // ✅ Send the transaction to the Incentive API and receive the incentive amount
        ResponseEntity<Incentive> response = restTemplate.postForEntity(
                "http://localhost:8080/incentive", // URL of the Incentive API
                transaction, // Send the whole Transaction object
                Incentive.class // Expecting a response of type Incentive
        );

        // ✅ Extract the incentive amount, default to 0 if response is null
        float incentiveAmount = (response.getBody() != null) ? response.getBody().getAmount() : 0f;

        // ✅ Deduct amount from the sender
        sender.setBalance(sender.getBalance() - transaction.getAmount());

        // ✅ Add amount + incentive to the recipient
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

        // ✅ Save the updated sender and recipient back to the database
        userRepository.save(sender);
        userRepository.save(recipient);

        // ✅ Create a new transaction record to save the transaction details
        TransactionRecord record = new TransactionRecord();
        record.setSender(sender); // Set who sent the money
        record.setRecipient(recipient); // Set who received the money
        record.setAmount(transaction.getAmount()); // Set how much was sent
        record.setIncentive(incentiveAmount); // ✅ Save the incentive received

        // ✅ Save the transaction record to the database
        transactionRecordRepository.save(record);
    }
}
