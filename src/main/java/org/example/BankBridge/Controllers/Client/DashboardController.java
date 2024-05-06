package org.example.BankBridge.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.BankBridge.App;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){}


    @FXML
    public void sendMoneyBtnOnClick(ActionEvent event) {
        String payeeAddress = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());

        if (App.firebaseService.findClientByUserAddressAndSendMoney(payeeAddress, amount)) {
            System.out.println("Successfully sent money.");
        } else {
            System.out.println("Could not send money.");
        }
    }



}
