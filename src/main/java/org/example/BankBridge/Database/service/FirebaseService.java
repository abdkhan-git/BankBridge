package org.example.BankBridge.Database.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Contains necessary database (CRUD) operations
 */
public class FirebaseService {

    public boolean registerUser(User user) {
        // Create user request with only email/phone num for authentication
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.email())
                .setPhoneNumber(user.phoneNum())
                .setDisabled(false);

        UserRecord record;
        try {
            record = App.fauth.createUser(request);
            addNewUserToDb(user, record.getUid());
            return true;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addNewUserToDb(User user, String uuid) {
        DocumentReference docRef = App.fstore.collection("users").document(UUID.randomUUID().toString());

        Map<String, Object> userData = new HashMap<>();
        userData.put("account_type", "CLIENT");
        userData.put("uuid", uuid);
        userData.put("first_name", user.firstName());
        userData.put("last_name", user.lastName());
        userData.put("email", user.email());
        userData.put("phone_num", user.phoneNum());
        userData.put("password", user.password());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(userData);
    }


}
