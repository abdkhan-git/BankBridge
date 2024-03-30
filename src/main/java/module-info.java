module org.example.BankBridge {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.BankBridge to javafx.fxml;
    exports org.example.BankBridge;
}