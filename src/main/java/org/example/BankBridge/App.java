package org.example.BankBridge;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.BankBridge.Models.Model;

public class App extends Application {

    // Creating app scene
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
