package org.example.BankBridge.Database.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.BankBridge.App;
import org.example.BankBridge.Database.model.User;
import org.example.BankBridge.Models.CheckingAccount;
import org.example.BankBridge.Models.Client;
import org.example.BankBridge.Models.SavingsAccount;
import org.w3c.dom.Document;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains necessary database (CRUD) operations
 */
public class FirebaseService {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public void addNewUserToDb(User user, String uuid) {
        DocumentReference docRef = App.fstore.collection("users").document(UUID.randomUUID().toString());

        Map<String, Object> userData = dataMap(
                "uuid", uuid,
                "email", user.email(),
                "password", user.password()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(userData);
    }

    public Client findClientByUserAddress(String userAddress) {
        Client client =  new Client("", "", "", "", "", LocalDate.now());
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("clients").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                System.out.println("Fetching data from firebase database..");
                for (QueryDocumentSnapshot document : documents) {
                    if (document.getData().get("userAddress").equals(userAddress)) {
                        System.out.println("SUCCESS: Found account with corresponding user address");
                        client = new Client(
                                document.getString("firstName"),
                                document.getString("lastName"),
                                document.getString("userAddress"),
                                document.getString("checkingAccountIDReference"),
                                document.getString("savingsAccountIDReference"),
                                LocalDate.parse(document.getString("dateCreated"))
                        );
                    }
                }
            } else {
                System.out.println("No client with specified address found.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return client;
    }

    public void addNewClientToDb(Client client) {
        DocumentReference docRef = App.fstore.collection("clients").document(UUID.randomUUID().toString());

        Map<String, Object> clientData = dataMap(
                "firstName", client.firstNameProperty().get(),
                "lastName", client.lastNameProperty().get(),
                "userAddress", client.uAddressProperty().get(),
                "checkingAccountIDReference", client.checkingAccountProperty().get(),
                "savingsAccountIDReference", client.savingsAccountProperty().get(),
                "dateCreated", client.dateProperty().getValue().toString()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(clientData);
    }

    public String addNewCheckingAccountToDb(CheckingAccount account) {
        DocumentReference docRef = App.fstore.collection("checking-accounts").document(UUID.randomUUID().toString());

        Map<String, Object> accountData = dataMap(
                "account_type", "CHECKING",
                "owner", account.ownerProperty().get(),
                "account_number", account.accountNumberProperty().get(),
                "balance", account.balanceProperty().get(),
                "transaction_limit", account.transactionLimitProperty().get()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(accountData);
        return docRef.getId();
    }

    public String addNewSavingsAccountToDb(SavingsAccount account) {
        DocumentReference docRef = App.fstore.collection("savings-accounts").document(UUID.randomUUID().toString());

        Map<String, Object> accountData = dataMap(
                "account_type", "SAVINGS",
                "owner", account.ownerProperty().get(),
                "account_number", account.accountNumberProperty().get(),
                "balance", account.balanceProperty().get(),
                "withdrawal_limit", account.withdrawalLimitProperty().get()
        );

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(accountData);
        return docRef.getId();
    }

//    public boolean findClientByUserAddressAndSendMoney(String address, double moneyToSend) {
//        ApiFuture<QuerySnapshot> future =  App.fstore.collection("clients").get();
//        List<QueryDocumentSnapshot> documents;
//        try {
//            documents = future.get().getDocuments();
//            if(!documents.isEmpty()) {
//                System.out.println("Fetching data from firebase database..");
//                for (QueryDocumentSnapshot document : documents) {
//                    if (document.getData().get("userAddress").equals(address)) {
//                        System.out.println("SUCCESS: Found account with corresponding user address");
//                        String checkingAccountRefId = document.getData().get("checkingAccountIDReference").toString();
//                        if (!checkingAccountRefId.equals("N/A")) {
//                            double currBalance = Double.parseDouble(
//                                    App.fstore.collection("checking-accounts")
//                                            .document(checkingAccountRefId)
//                                            .get()
//                                            .get()
//                                            .get("balance")
//                                            .toString()
//                            );
//                            double newBalance = currBalance + moneyToSend;
//                            DocumentReference accDocRef = App.fstore.collection("checking-accounts").document(checkingAccountRefId);
//                            accDocRef.update("balance", newBalance);
//                            return true;
//                        }
//                    }
//                }
//            } else {
//                System.out.println("No data");
//            }
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }

    public boolean findClientByUserAddressAndSendMoney(String address, double moneyToSend) {
        Firestore db = App.fstore;

        List<QueryDocumentSnapshot> documents = getClientDocuments(db);
        if (documents != null && !documents.isEmpty()) {
            for (QueryDocumentSnapshot document : documents) {
                if (document.getData().containsKey("userAddress") && document.getString("userAddress").equals(address)) {
                    LOGGER.info("Found account with corresponding user address: " + address);
                    String checkingAccountRefId = document.getString("checkingAccountIDReference");
                    if (checkingAccountRefId != null && !checkingAccountRefId.equals("N/A")) {
                        updateCheckingAccountBalance(db, checkingAccountRefId, moneyToSend);
                        return true;
                    }
                }
            }
        } else {
            LOGGER.warning("No client documents found");
        }
        return false;
    }

    private List<QueryDocumentSnapshot> getClientDocuments(Firestore db) {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("clients").get();
            return future.get().getDocuments();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching client documents", ex);
            return null;
        }
    }

    private void updateCheckingAccountBalance(Firestore db, String checkingAccountRefId, double moneyToSend) {
        try {
            DocumentReference accDocRef = db.collection("checking-accounts").document(checkingAccountRefId);
            double currBalance = accDocRef.get().get().getDouble("balance");
            double newBalance = currBalance + moneyToSend;
            accDocRef.update("balance", newBalance);
            LOGGER.info("Balance updated successfully");
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, "Error updating balance", ex);
        }
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
                                String.valueOf(document.get("email")),
                                String.valueOf(document.get("password"))
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

//    public List<Account> retrieveAllBankAccounts() {
//        List<Account> listOfAccounts = new ArrayList<>();
//
//        ApiFuture<QuerySnapshot> future =  App.fstore.collection("accounts").get();
//        List<QueryDocumentSnapshot> documents;
//
//        try {
//            documents = future.get().getDocuments();
//            if(!documents.isEmpty()) {
//                documents.stream()
//                        .map(document -> new Account(
//                                String.valueOf(document.get("account_number")),
//                                String.valueOf(document.get("account_type")),
//                                Double.parseDouble(String.valueOf(document.get("balance"))),
//                                String.valueOf(document.get("date_created"))
//                        ))
//                        .forEach(listOfAccounts::add);
//            }
//            else {
//                System.out.println("No bank accounts found within database.");
//            }
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
//        return listOfAccounts;
//    }

    public String retrievePersonByEmailAndReturnPass(String email) {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("users").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                System.out.println("Fetching data from firebase database..");
                for (QueryDocumentSnapshot document : documents) {
                    if (document.getData().get("email").equals(email)) {
                        System.out.println("SUCCESS: Found person with corresponding email");
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
