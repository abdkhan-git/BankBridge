package org.example.BankBridge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.BankBridge.database.FirebaseInitialize;

import java.io.IOException;

public class BankBridgeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankBridgeApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        FirebaseInitialize.initialize();
    }

    public static void main(String[] args) {
        launch();
    }
}