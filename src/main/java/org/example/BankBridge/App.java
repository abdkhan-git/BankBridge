package org.example.BankBridge;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BankBridge.Database.config.FirebaseConfig;

public class App extends Application {
    public static Firestore fstore;
    public static FirebaseAuth fauth;
    private final FirebaseConfig config = new FirebaseConfig();

    // Creating app scene
    @Override
    public void start(Stage stage) throws Exception {

        fstore = config.initialize();
        fauth = FirebaseAuth.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
