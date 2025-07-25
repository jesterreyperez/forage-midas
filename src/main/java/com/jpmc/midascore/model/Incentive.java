// This file defines the Incentive class inside the 'model' package
package com.jpmc.midascore.model;

// This is the class that represents the incentive data (bonus for the recipient)
public class Incentive {

    // This field stores the incentive amount (bonus money)
    private float amount;

    // This is a getter method — it returns the current value of 'amount'
    public float getAmount() {
        return amount;
    }

    // This is a setter method — it allows us to change the value of 'amount'
    public void setAmount(float amount) {
        this.amount = amount;
    }
}
