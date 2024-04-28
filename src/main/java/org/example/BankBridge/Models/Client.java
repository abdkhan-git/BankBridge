package org.example.BankBridge.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.BankBridge.Database.model.Account;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty userAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String fName, String lName, String uAddress, Account cAccount, Account sAccount, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "FirstName", fName);
        this.lastName = new SimpleStringProperty(this, "LastName", lName);
        this.userAddress = new SimpleStringProperty(this, "User Address", uAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", cAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", sAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date Created", date);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty uAddressProperty() {
        return userAddress;
    }
    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }
    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }

}
