package org.example.BankBridge.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.BankBridge.Database.model.Account;
import org.example.BankBridge.Database.model.User;
import org.example.BankBridge.Database.service.FirebaseService;

import java.time.LocalDate;

public class CreateClientController {
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

    private final FirebaseService firebaseService = new FirebaseService();

    @FXML
    public void createClientBtnOnClick(ActionEvent e) {
        String accountType = ch_acc_box.isSelected() ? "Checking Account" : "Savings Account";
        User user = new User(fName_fld.getText(),
                lName_fld.getText(),
                "placeholderEmail",
                "placeholderPhoneNum",
                password_fld.getText(),
                accountType);

        firebaseService.registerUser(user);

        Account acc = new Account("128 409 613", accountType, 0.0, LocalDate.now().toString());
        firebaseService.addNewBankAccountToDb(acc);
    }
}
