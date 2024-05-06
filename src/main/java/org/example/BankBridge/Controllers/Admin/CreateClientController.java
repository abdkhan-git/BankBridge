package org.example.BankBridge.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.BankBridge.App;
import org.example.BankBridge.Models.Account;
import org.example.BankBridge.Models.CheckingAccount;
import org.example.BankBridge.Models.Client;
import org.example.BankBridge.Models.SavingsAccount;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public Label pAddress_lbl;
    public CheckBox pAddress_box;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Label error_lbl;
    public Button createclient_btn;

    private String payeeAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private String generateAccountNumber() {
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999) + 1000);
        return firstSection + " " + lastSection;
    }

    private Account createAccount(String accountType) {
        double balance = Double.parseDouble(ch_amount_fld.getText());
        String accountNumber = generateAccountNumber();
        String fullName = fName_fld.getText() + " " + lName_fld.getText();

        if (accountType.equals("Checking")) {
            return new CheckingAccount(fullName, accountNumber, balance, 10);
        } else if (accountType.equals("Savings")) {
            return new SavingsAccount(fullName, accountNumber, balance, 10);
        }
        return null;
    }

    public static String generateUserAddress(String firstName, String lastName) {
        // Generate a unique identifier for the account address
        String uniqueID = UUID.randomUUID().toString();

        // Combine first name, last name, and a portion of the unique identifier to create the account address
        String userAddress = firstName.toLowerCase() + "." + lastName.toLowerCase() + "." + uniqueID.substring(0, 6);

        return userAddress;
    }

    @FXML
    public void createNewClientBtnOnClick(ActionEvent e) {
        CheckingAccount checkingAccount = new CheckingAccount("N/A", "N/A", 0, 0);
        SavingsAccount savingsAccount = new SavingsAccount("N/A", "N/A", 0, 0);
        String checkingAccRefId = "N/A";
        String savingsAccRefId = "N/A";

        if (ch_acc_box.isSelected()) {
            checkingAccount = (CheckingAccount) createAccount("Checking");
            checkingAccRefId = App.firebaseService.addNewCheckingAccountToDb(checkingAccount);
        }
        if (sv_acc_box.isSelected()) {
            savingsAccount = (SavingsAccount) createAccount("Savings");
            savingsAccRefId = App.firebaseService.addNewSavingsAccountToDb(savingsAccount);
        }
        String pAddress = pAddress_box.isSelected() ? pAddress_lbl.getText() : "";

        Client client = new Client(
                fName_fld.getText(),
                lName_fld.getText(),
                generateUserAddress(fName_fld.getText(), lName_fld.getText()),
                checkingAccRefId,
                savingsAccRefId,
                LocalDate.now()
        );
        App.firebaseService.addNewClientToDb(client);
    }
}
