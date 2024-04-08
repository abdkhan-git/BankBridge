package org.example.BankBridge.Database.model;

import java.util.Date;

public record Account(String accountNumber,
                      String accountType,
                      double balance,
                      String dateCreated) {
}


