package org.example.BankBridge.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.ClientMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboardBtn;
    public Button transactionBtn;
    public Button accountsBtn;
    public Button profileBtn;
    public Button logoutBtn;
    public Button reportBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addListeners();
    }

    private void addListeners() {
        dashboardBtn.setOnAction(event -> onDashboard());
        transactionBtn.setOnAction(event -> onTransactions());
        accountsBtn.setOnAction(event -> onAccounts());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }

}
