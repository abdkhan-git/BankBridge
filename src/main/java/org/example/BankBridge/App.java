package org.example.BankBridge;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BankBridge.Database.config.FirebaseConfig;
import org.example.BankBridge.Database.service.FirebaseService;

public class App extends Application {
    public static Firestore fstore;
    public static FirebaseAuth fauth;

    public static FirebaseService firebaseService;

    private final FirebaseConfig config = new FirebaseConfig();

    // Creating app scene
    @Override
    public void start(Stage stage) throws Exception {

        fstore = config.initialize();
        fauth = FirebaseAuth.getInstance();
        firebaseService = new FirebaseService();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}
