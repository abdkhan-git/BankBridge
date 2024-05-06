package org.example.BankBridge.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.service.FirebaseService;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {

    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_ch_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void transferToSavingsAccountBtnOnClick(ActionEvent event) {
        double amount = Double.parseDouble(amount_to_sv.getText());
//        App.firebaseService.updateAccountWithNewBalance(sv_acc_num.getText(), amount);
    }

    @FXML
    public void transferToCheckingsAccountBtnOnClick(ActionEvent event) {
        double amount = Double.parseDouble(amount_to_ch.getText());
//        App.firebaseService.updateAccountWithNewBalance(ch_acc_num.getText(), amount);
    }

}
