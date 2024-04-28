package org.example.BankBridge.Models;

import javafx.beans.property.*;

public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;

    public Account(String owner, String accountNumber, double balance) {
        this.owner = new SimpleStringProperty(this, "Owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
    }

    public StringProperty ownerProperty() {
        return owner;
    }
    public StringProperty accountNumberProperty() {
        return accountNumber;
    }
    public DoubleProperty balanceProperty() {
        return balance;
    }

}
