package com.jpmc.midascore.model; // This line defines the package (folder) where this class is located

import jakarta.persistence.*; // Imports all JPA (Java Persistence API) tools used to map Java classes to database tables
import java.math.BigDecimal; // BigDecimal is used to handle money values accurately (no rounding errors)

@Entity // Tells Spring/JPA that this class should be saved as a table in the database
public class User {

    @Id // Marks this field as the primary key (unique ID for each row in the table)
    private String userId; // The user's unique ID (used as sender or recipient)

    @Column(nullable = false) // This field must have a value (cannot be null in the database)
    private BigDecimal balance; // The user's current balance in their account

    // Getter method to return the userId value
    public String getUserId() {
        return userId;
    }

    // Setter method to set or update the userId value
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter method to return the balance value
    public BigDecimal getBalance() {
        return balance;
    }

    // Setter method to set or update the balance value
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
