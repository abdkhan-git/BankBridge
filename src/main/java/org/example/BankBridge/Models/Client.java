package org.example.BankBridge.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty userAddress;
    private final StringProperty checkingAccountRef;
    private final StringProperty savingsAccountRef;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String fName, String lName, String uAddress, String checkingAccountRef, String savingAccountRef, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "FirstName", fName);
        this.lastName = new SimpleStringProperty(this, "LastName", lName);
        this.userAddress = new SimpleStringProperty(this, "User Address", uAddress);
        this.checkingAccountRef = new SimpleStringProperty(this, "Checking Account", checkingAccountRef);
        this.savingsAccountRef = new SimpleStringProperty(this, "Savings Account", savingAccountRef);
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
    public StringProperty checkingAccountProperty() {
        return checkingAccountRef;
    }
    public StringProperty savingsAccountProperty() {
        return savingsAccountRef;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }

}
