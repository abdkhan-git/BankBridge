package org.example.BankBridge.Database.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.model.Account;
import org.example.BankBridge.Database.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

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

        Map<String, Object> userData = dataMap(
                "account_type", "CLIENT",
                "uuid", uuid,
                "first_name", user.firstName(),
                "last_name", user.lastName(),
                "email", user.email(),
                "phone_num", user.phoneNum(),
                "password", user.password()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(userData);
    }

    public void addNewBankAccountToDb(Account account) {
        DocumentReference docRef = App.fstore.collection("accounts").document(UUID.randomUUID().toString());

        Map<String, Object> accountData = dataMap(
                "account_type", "CHECKING",
                "account_number", account.accountNumber(),
                "balance", account.balance(),
                "date_created", account.dateCreated()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(accountData);
    }

    public boolean updateAccountWithNewBalance(String address, double balance) {
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("account").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if(!documents.isEmpty()) {
                System.out.println("Fetching data from firebase database..");
                for (QueryDocumentSnapshot document : documents) {
                    if (document.getData().get("user_address").equals(address)) {
                        System.out.println("SUCCESS: Found account with corresponding user address");
                        DocumentReference docRef = document.getReference();
                        docRef.update("balance", balance).get(); // Update balance field
                        return true;
                    }
                }
            } else {
                System.out.println("No data");
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<User> retrieveAllRegisteredUsers() {
        List<User> listOfRegisteredUsers = new ArrayList<>();

        ApiFuture<QuerySnapshot> future =  App.fstore.collection("users").get();
        List<QueryDocumentSnapshot> documents;

        try {
            documents = future.get().getDocuments();
            if(!documents.isEmpty()) {
                documents.stream()
                        .map(document -> new User(
                                String.valueOf(document.get("first_name")),
                                String.valueOf(document.get("last_name")),
                                String.valueOf(document.get("email")),
                                String.valueOf(document.get("phone_num")),
                                String.valueOf(document.get("password")),
                                String.valueOf(document.get("account_type")),
                                String.valueOf(document.get("user_address"))
                        ))
                        .forEach(listOfRegisteredUsers::add);
            }
            else {
                System.out.println("No registered users found within database.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return listOfRegisteredUsers;
    }

    public List<Account> retrieveAllBankAccounts() {
        List<Account> listOfAccounts = new ArrayList<>();

        ApiFuture<QuerySnapshot> future =  App.fstore.collection("accounts").get();
        List<QueryDocumentSnapshot> documents;

        try {
            documents = future.get().getDocuments();
            if(!documents.isEmpty()) {
                documents.stream()
                        .map(document -> new Account(
                                String.valueOf(document.get("account_number")),
                                String.valueOf(document.get("account_type")),
                                Double.parseDouble(String.valueOf(document.get("balance"))),
                                String.valueOf(document.get("date_created"))
                        ))
                        .forEach(listOfAccounts::add);
            }
            else {
                System.out.println("No bank accounts found within database.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return listOfAccounts;
    }

    public String retrievePersonByUuidAndReturnPass(String uuid) {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("users").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try
        {
            documents = future.get().getDocuments();
            if(!documents.isEmpty()) {
                System.out.println("Fetching data from firebase database..");
                for (QueryDocumentSnapshot document : documents) {
                    if (document.getData().get("uuid").equals(uuid)) {
                        System.out.println("SUCCESS: Found person with corresponding UUID");
                        return document.getData().get("password").toString();
                    }
                }
            } else {
                System.out.println("No data");
            }
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // Helper method for easier insertion of data in the form of Map<String, Object>
    private Map<String, Object> dataMap(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments. Must be key-value pairs.");
        }

        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = String.valueOf(keyValuePairs[i]);
            Object value = keyValuePairs[i + 1];
            data.put(key, value);
        }
        return data;
    }


}
