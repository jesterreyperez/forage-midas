package com.jpmc.midascore; // Declares this file is part of the midascore package

import com.jpmc.midascore.foundation.Transaction; // Imports the incoming transaction class
import com.jpmc.midascore.entity.TransactionRecord; // Our new transaction database entity
import com.jpmc.midascore.entity.UserRecord; // The user database entity

import com.jpmc.midascore.repository.TransactionRecordRepository; // To save transactions
import com.jpmc.midascore.repository.UserRepository; // To look up and update users

import org.springframework.beans.factory.annotation.Autowired; // Lets us inject dependencies
import org.springframework.beans.factory.annotation.Value; // Gets values from application.yml
import org.springframework.kafka.annotation.KafkaListener; // Lets us listen to Kafka messages
import org.springframework.stereotype.Component; // Marks this class as a Spring component

@Component // Spring will create and manage this class as a bean
public class TransactionListener {

    @Value("${general.kafka-topic}") // Gets the topic name from application.yml
    private String topic;

    @Autowired
    private UserRepository userRepository; // Injects our user repository

    @Autowired
    private TransactionRecordRepository transactionRecordRepository; // Injects our transaction record repository

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core") // Listens for messages from Kafka
    public void listen(Transaction transaction) {
        // Try to find the sender and recipient in the database
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // If either user is missing, stop — invalid transaction
        if (sender == null || recipient == null) {
            return;
        }

        // If the sender doesn't have enough money, stop
        if (sender.getBalance() < transaction.getAmount()) {
            return;
        }

        // Update both balances
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

        // Save updated balances to the database
        userRepository.save(sender);
        userRepository.save(recipient);

        // Create a record of the transaction
        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(transaction.getAmount());

        // Save the transaction record to the database
        transactionRecordRepository.save(record);
    }
}
