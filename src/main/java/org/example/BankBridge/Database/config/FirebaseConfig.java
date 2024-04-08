package org.example.BankBridge.Database.config;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;

/**
 * Config class to initialize Firebase connection
 */
public class FirebaseConfig {
    public Firestore initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/org/example/BankBridge/key.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FirestoreClient.getFirestore();
    }
}
