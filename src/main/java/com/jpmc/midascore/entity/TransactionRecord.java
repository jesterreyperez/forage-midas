package com.jpmc.midascore.entity; // This defines which package (folder group) this class is part of

import jakarta.persistence.*; // Imports annotations used for database mapping (JPA)

// This class will represent a row in the database table called "transaction_record"
@Entity // Tells Spring this class is a database entity (a table)
public class TransactionRecord {

    // This is the primary key (unique ID) for each transaction in the database
    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // The database will auto-generate the ID
    private Long id;

    // This refers to the sender of the transaction
    @ManyToOne // Many transactions can have the same sender
    private UserRecord sender;

    // This refers to the recipient of the transaction
    @ManyToOne // Many transactions can have the same recipient
    private UserRecord recipient;

    // This is the amount of money being transferred in the transaction
    private float amount;

    // ✅ This is the incentive amount added to the recipient's balance
    private float incentive;

    // ===== Getters and Setters (used to access and update the private fields) =====

    public Long getId() {
        return id; // Returns the transaction ID
    }

    public UserRecord getSender() {
        return sender; // Returns the sender
    }

    public void setSender(UserRecord sender) {
        this.sender = sender; // Sets the sender
    }

    public UserRecord getRecipient() {
        return recipient; // Returns the recipient
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient; // Sets the recipient
    }

    public float getAmount() {
        return amount; // Returns the transaction amount
    }

    public void setAmount(float amount) {
        this.amount = amount; // Sets the transaction amount
    }

    public float getIncentive () {
        return incentive; // Returns the incentive amount
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive; // Sets the incentive amount
    }
}
