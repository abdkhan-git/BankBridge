package org.example.BankBridge.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.example.BankBridge.Controllers.Client.TransactionCellController;
import org.example.BankBridge.Models.Transcation;

public class TransactionCellFactory extends ListCell<Transcation> {
    @Override
    protected void updateItem(Transcation transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
