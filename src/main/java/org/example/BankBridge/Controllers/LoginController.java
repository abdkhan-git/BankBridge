package org.example.BankBridge.Controllers;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.service.FirebaseService;
import org.example.BankBridge.Models.Client;
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
    }

    @FXML
    public void onLoginBtnClick() {
        String address = user_address_fld.getText();
        String password = password_fld.getText();
        if (acc_selector.getValue() == AccountType.CLIENT) {
            App.firebaseService.findClientByUserAddress(address);
            changeScene();
        } else if (acc_selector.getValue() == AccountType.ADMIN) {
            try {
                UserRecord user = App.fauth.getUserByEmail(address);
                if (user != null) {
                    if (password.equals(App.firebaseService.retrievePersonByEmailAndReturnPass(address))) {
                        // switch screens
                        changeScene();
                    } else {
                        System.out.println("ERROR: Invalid password.");
                    }
                }
            } catch (FirebaseAuthException e) {
                System.out.println("ERROR: Could not sign in. Email may be incorrect.");
            }
        }


    }

    private void changeScene(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        if(Model.getInstance().getViewFactory().getLoginAccountType()==AccountType.CLIENT){
            Model.getInstance().getViewFactory().showClientWindow();
        }
        else{
            Model.getInstance().getViewFactory().showAdminWindow();
        }
    }
}
