/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankingproject.service;

import com.mycompany.onlinebankingproject.database.Database;
import com.mycompany.onlinebankingproject.model.Account;
import com.mycompany.onlinebankingproject.model.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */
public class TransactionService {
    
    CustomerService cs = new CustomerService();
    AccountService as = new AccountService();
    Database db = cs.getDatabase();
    
    // retrieve all transactions for given the accountID
    public List<Transaction> getAllTransactionsPerAccountDB(String customerID, String password, String accountID){
        Account a = as.getAccountByIDDB(customerID, password, accountID);
        return a.getTransactions();
    }
    
    // retrieve single transaction given the transactionID
    public Transaction getTransactionByIDDB(String customerID, String password, String accountID, String transactionID){
        List <Transaction> lt = getAllTransactionsPerAccountDB(customerID, password, accountID);
        Transaction tmatch = new Transaction();
        for (Transaction t : lt){
            if(transactionID.equalsIgnoreCase(t.getTransID())){
                tmatch = t;
            }
        }
        return tmatch;
    }
    
    
    // create a withdrawal transaction for a given accountID - amount is deducted from currentBalance
    // using BigDecimal for data validation on the double balance to get only 2 digits after comma 
    public Transaction createWithdrawalDB(String customerID, String password, String accountID, double amountt, String description){
            
        Transaction newt = new Transaction();
        Account a = as.getAccountByIDDB(customerID, password, accountID);

        BigDecimal bd = new BigDecimal(amountt).setScale(2, RoundingMode.HALF_UP);
        double amountTwoDec = bd.doubleValue();

        String transID = "t" + (db.getAlltransactionsDB().size()+1);
        a.setCurrentBalance(a.getCurrentBalance() - amountTwoDec);
        newt = new Transaction(transID, "debit", amountTwoDec, description, a.getCurrentBalance());

        List<Transaction> lt = getAllTransactionsPerAccountDB(customerID, password, accountID);
        lt.add(newt);
        db.settransactionDB(newt);
        return newt;
    }

    // create a lodgement operation for a given accountID - amount is added to currentBalance
    // using BigDecimal for data validation on the double balance to get only 2 digits after comma
    public Transaction createLodgementDB(String customerID, String password, String accountID, double amountt, String description){
        Transaction newt = new Transaction();
        Account a = as.getAccountByIDDB(customerID, password, accountID);

        BigDecimal bd = new BigDecimal(amountt).setScale(2, RoundingMode.HALF_UP);
        amountt = bd.doubleValue();
        
        String transID = "t" + (db.getAlltransactionsDB().size()+1);
        a.setCurrentBalance(a.getCurrentBalance() + amountt);
        newt = new Transaction(transID, "credit", amountt, description, a.getCurrentBalance());

        List<Transaction> lt = getAllTransactionsPerAccountDB(customerID, password, accountID);
        lt.add(newt);
        db.settransactionDB(newt);

        return newt;
    }

    // create a transfer operation for a given source accountID and destination accountID
    // amount is deduced from source account and added to destination account
    // balances from source account and destination account is recalculated
    // using BigDecimal for data validation on the double balance to get only 2 digits after comma
    public Transaction createTransferDB(String customerID, String password, String accountIDSource, String accountIDDestination, double amountt, String description){
        
        BigDecimal bd = new BigDecimal(amountt).setScale(2, RoundingMode.HALF_UP);
        double amountTwoDec = bd.doubleValue();
        
        Account a1 = as.getAccountByIDDB(customerID, password, accountIDSource);

        List<Account> alla = db.getAllAccountsDB(); //need to find the correct destination account
        Account a2 = new Account();
        for(Account a : alla){
            if(accountIDDestination.equalsIgnoreCase(a.getAccountNumber())){
                a2 = a;
            }
        }
        
        Transaction newt1 = new Transaction(); 

        if(a2.getAccountNumber() != null && !(a2.getAccountNumber().equalsIgnoreCase(a1.getAccountNumber()))){ //if account destination found and account source and destination are not the same
            List<Transaction> lt1 = getAllTransactionsPerAccountDB(customerID, password, accountIDSource);
            List<Transaction> lt2 = a2.getTransactions();

            String transID1 = "t" + (db.getAlltransactionsDB().size()+1);
            a1.setCurrentBalance(a1.getCurrentBalance() - amountTwoDec);
            newt1 = new Transaction(transID1, "debit", amountTwoDec, description, a1.getCurrentBalance());
            db.settransactionDB(newt1);

            String transID2 = "t" + (db.getAlltransactionsDB().size()+1);
            
            double newBalance = a2.getCurrentBalance() + amountTwoDec;
            
            bd = new BigDecimal(newBalance).setScale(2, RoundingMode.HALF_UP);
            double balanceTwoDec = bd.doubleValue();
            
            a2.setCurrentBalance(balanceTwoDec);
            Transaction newt2 = new Transaction(transID2, "credit", amountTwoDec, description, a2.getCurrentBalance());
            db.settransactionDB(newt2);

            lt1.add(newt1);
            lt2.add(newt2);
        }
        
        return newt1;
    }
    
    
    
}
