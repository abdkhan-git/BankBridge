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
import org.example.BankBridge.Database.model.User;
import org.example.BankBridge.Models.Client;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AccountType;

public class SignUpController {
    public TextField tfEmailAddress;
    public PasswordField tfPassword;
    public Button btnRegister;
    public Button btnSkipToLogin;

    @FXML
    public void registerBtnOnClick(ActionEvent event) {
        String email = tfEmailAddress.getText();
        String pass = tfPassword.getText();

        User user = new User(email, pass);

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(pass)
                .setDisabled(false);

        UserRecord record;
        try {
            record = App.fauth.createUser(request);
            App.firebaseService.addNewUserToDb(user, record.getUid());
            changeToLoginWindowScene();
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void skipToLoginBtnOnClick(ActionEvent actionEvent) {
        changeToLoginWindowScene();
    }

    public void changeToLoginWindowScene() {
        Stage stage = (Stage) tfEmailAddress.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
