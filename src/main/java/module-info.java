module org.example.BankBridge {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.xerial.sqlitejdbc;
    requires org.mockito;

    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;

    // Add these lines to include Mockito and JUnit dependencies


    opens org.example.BankBridge to javafx.fxml;
    exports org.example.BankBridge;
    exports org.example.BankBridge.Controllers;
    exports org.example.BankBridge.Controllers.Admin;
    exports org.example.BankBridge.Controllers.Client;
    exports org.example.BankBridge.Models;
    exports org.example.BankBridge.Views;
}
