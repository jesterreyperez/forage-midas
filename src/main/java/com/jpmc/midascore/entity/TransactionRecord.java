package com.jpmc.midascore.entity; // This is where the file belongs

import jakarta.persistence.*;

@Entity // Marks this class as a table in the database
public class TransactionRecord {

    @Id // Primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private Long id;

    @ManyToOne // Many transactions can have one sender
    private UserRecord sender;

    @ManyToOne // Many transactions can have one recipient
    private UserRecord recipient;

    private float amount; // The amount of money transferred

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
