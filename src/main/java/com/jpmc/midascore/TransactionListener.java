package com.jpmc.midascore; // Declares the package location of this class

import org.springframework.kafka.annotation.KafkaListener; // Lets us listen to Kafka topics
import org.springframework.stereotype.Component; // Marks this class as a Spring-managed component (bean)
import org.springframework.beans.factory.annotation.Value; // Allows us to inject values from application.yml

@Component // Tells Spring to create and manage an instance of this class
public class TransactionListener {
    @Value("${general.kafka-topic}") // Reads the topic name from application.yml and injects it into this variable
    private String topic;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    // Listens to the Kafka topic using the name from application.yml
    // groupId identifies the consumer group for this listener
    public void listen(Transaction transaction) {
        // This method runs every time a Kafka message (a Transaction) is received
        System.out.println("Received transaction: " + transaction); // Logs the received transaction
    }
}
