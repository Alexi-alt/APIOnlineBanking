/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankingproject.service;

import com.mycompany.onlinebankingproject.database.Database;
import com.mycompany.onlinebankingproject.model.Account;
import com.mycompany.onlinebankingproject.model.Customer;
import com.mycompany.onlinebankingproject.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */
public class AccountService {
    
    CustomerService cs = new CustomerService();
    Database db = cs.getDatabase();
    
    // retrieve all accounts for the given customerID with password (for validation)
    public List<Account> getAllAccountsPerCustomerDB(String customerID, String password){
        Customer c = cs.getCustomerByIDDB(customerID, password);
        return c.getCustomerAccounts();
    }
    
    // retrieve single account based on account ID
    public Account getAccountByIDDB(String customerID, String password, String accountID){
        List<Account> la = getAllAccountsPerCustomerDB(customerID, password);
        Account amatch = new Account();
        for(Account a : la){
            if(accountID.equalsIgnoreCase(a.getAccountNumber())){
                amatch = a;
            }
        }
        return amatch;
    }
    
    // create account for given customerID
    public Account createAccountDB(String customerID, String password, String accountType, String sortCode){
        
        if(cs.getCustomerByIDDB(customerID, password).getCustomerID() != null){
            List<Account> la = getAllAccountsPerCustomerDB(customerID, password);
            String accountID = "a" + (db.getAllAccountsDB().size()+1);
            List<Transaction> lt = new ArrayList<>();
            Account newa = new Account (accountID, accountType, sortCode, 0.0, lt);
            la.add(newa);
            db.setAccountDB(newa);
            return newa;
        }
        return null;
    }  
    
}
