package org.example.BankBridge.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.BankBridge.Models.Model;
import org.example.BankBridge.Views.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {}

    private void onCreateClient()
    {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

}
