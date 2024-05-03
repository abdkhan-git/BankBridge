package org.example.BankBridge.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AccountType;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

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

    private void createCheckingAccount(){
        double balance = Double.parseDouble(ch_amount_fld.getText());
        //Generate Account Number

        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999)+1000);
        String accountNumber = firstSection + " " + lastSection;
        //Create the Checking number
        // creating this later
    // Model.getInstance().getDatabaseDriver.createCheckingAccount(payeeAddress, accountNumber, /* Transaction Limit*/10, balance);
    }
}
