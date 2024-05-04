package org.example.BankBridge.Controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.BankBridge.App;
import org.example.BankBridge.Models.Client;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AccountType;

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
            changeToLoginWindowScene();
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    public void changeToLoginWindowScene() {
        Stage stage = (Stage) tfEmailAddress.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
