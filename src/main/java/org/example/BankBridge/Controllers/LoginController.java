package org.example.BankBridge.Controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.service.FirebaseService;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AccountType;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ChoiceBox<AccountType> acc_selector;
    public Label user_address_lbl;
    public TextField user_address_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
       if(Model.getInstance().getViewFactory().getLoginAccountType()==AccountType.CLIENT){
           Model.getInstance().getViewFactory().showClientWindow();
       }
       else{
           Model.getInstance().getViewFactory().showAdminWindow();
       }
    }

    public void login() {
        String email = user_address_lbl.getText();
        String password = password_fld.getText();

        try {
            UserRecord user = App.fauth.getUserByEmail(email);
            if (user != null) {
                String uuid = user.getUid();
                if (password.equals(App.firebaseService.retrievePersonByUuidAndReturnPass(uuid))) {
                    // switch screens
                } else {
                    System.out.println("ERROR: Invalid password.");
                }
            }
        } catch (FirebaseAuthException e) {
            System.out.println("ERROR: Could not sign in.");
        }

    }
}
