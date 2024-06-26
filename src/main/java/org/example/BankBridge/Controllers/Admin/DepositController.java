package org.example.BankBridge.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    public TextField pAddress_fld;
    public Button search_btn;
    public ListView result_listview;
    public TextField amount_fld;
    public Button deposit_btn;
    public Text payeeAddresslbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void searchBtnOnClick() {
        boolean userFound = App.firebaseService.findClientByUserAddressBool(pAddress_fld.getText());
        if (userFound) {
            payeeAddresslbl.setText("Client Found");
        }
    }

    @FXML
    public void depositBtnOnClick(ActionEvent event) {
        App.firebaseService.findClientByUserAddressAndSendMoney(pAddress_fld.getText(), Double.parseDouble(amount_fld.getText()));
        payeeAddresslbl.setText("Search by Payee Address");
    }
}
