package org.example.BankBridge.Controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.BankBridge.App;
import org.example.BankBridge.Models.Client;

public class SignUpController {
    public TextField tfEmailAddress;
    public PasswordField tfPassword;
    public Button btnRegister;

    @FXML
    public void registerBtnOnClick(ActionEvent event) {
        String email = tfEmailAddress.getText();
        String pass = tfPassword.getText();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(pass)
                .setDisabled(false);

        UserRecord record;
        try {
            record = App.fauth.createUser(request);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }
}
