package org.example.BankBridge.Database.config;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseConfig {
    // Initialization method for connecting Firebase
    public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/org/example/BankBridge/key.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
