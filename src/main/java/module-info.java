module org.example.BankBridge {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.auth.oauth2;


    opens org.example.BankBridge to javafx.fxml;
    exports org.example.BankBridge;
}