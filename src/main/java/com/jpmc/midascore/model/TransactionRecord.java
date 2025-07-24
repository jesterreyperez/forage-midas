package com.jpmc.midascore.model; // This class belongs to the 'model' package

import jakarta.persistence.*; // For database annotations
import java.math.BigDecimal; // Used for accurate financial values
import java.time.LocalDateTime; // To store date and time of transaction

@Entity // This class will be stored as a table in the database
public class TransactionRecord {

    @Id // This field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates unique ID
    private Long id; // Unique transaction record ID

    @ManyToOne // Many transactions can be linked to one sender
    @JoinColumn(name = "sender_id") // DB column to store sender ID
    private User sender; // Reference to sender user

    @ManyToOne // Many transactions can be linked to one recipient
    @JoinColumn(name = "recipient_id") // DB column to store recipient ID
    private User recipient; // Reference to recipient user

    @Column(nullable = false) // Cannot be null in the database
    private BigDecimal amount; // Amount of money transferred

    @Column(nullable = false) // Cannot be null in the database
    private LocalDateTime timestamp; // When the transaction happened

    // Getters and setters so Spring can access and update data

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
