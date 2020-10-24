/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankingproject.database;

import com.mycompany.onlinebankingproject.model.Account;
import com.mycompany.onlinebankingproject.model.Customer;
import com.mycompany.onlinebankingproject.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */
public class Database {
    
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Account> allAccounts = new ArrayList<>();
    private List<Transaction> allTransactions = new ArrayList<>();
    public static boolean init = true;
    
    public Database(){
        
        if(init){
            
            Transaction t1 = new Transaction("t1", "credit", 50, "salary", 50);
            List<Transaction> lta1 = new ArrayList<>();
            lta1.add(t1);
            
            Transaction t2 = new Transaction("t1", "debit", 2.05, "shopping", 2325);
            Transaction t3 = new Transaction("t2", "credit", 140, "refund", 2465);
            List<Transaction> lta2 = new ArrayList<>();
            lta2.add(t2);
            lta2.add(t3);

            Transaction t4 = new Transaction("t3", "debit", 49.94, "cigarettes", 2952);
            Transaction t5 = new Transaction("t4", "debit", 75.99, "games", 2876.01);
            List<Transaction> lta3 = new ArrayList<>();
            lta3.add(t4);
            lta3.add(t5);

            Transaction t6 = new Transaction("t6", "credit", 10.67, "refund", 931.75);
            Transaction t7 = new Transaction("t7", "debit", 2.03, "airport", 929.72);
            Transaction t8 = new Transaction("t8", "debit", 367.07, "travel", 562.65);
            List<Transaction> lta4 = new ArrayList<>();
            lta4.add(t6);
            lta4.add(t7);
            lta4.add(t8);

            allTransactions.add(t1);
            allTransactions.add(t2);
            allTransactions.add(t3);
            allTransactions.add(t4);
            allTransactions.add(t5);
            allTransactions.add(t6);
            allTransactions.add(t7);
            allTransactions.add(t8);
            

            Account a1 = new Account("a1", "savings", "abc123", 50.0, lta1);
            List<Account> lac1 = new ArrayList<>();
            lac1.add(a1);

            Account a2 = new Account("a2", "current", "abc123", 2465.0, lta2);
            List<Account> lac2 = new ArrayList<>();
            lac2.add(a2);

            Account a3 = new Account("a3", "current", "def456", 2876.01, lta3);
            Account a4 = new Account("a4", "savings", "def456", 562.65, lta4);
            List<Account> lac3 = new ArrayList<>();
            lac3.add(a3);
            lac3.add(a4);


            allAccounts.add(a1);
            allAccounts.add(a2);
            allAccounts.add(a3);
            allAccounts.add(a4);


            Customer c1 = new Customer("c1", "Alexia", "Falcoz", "Castleforbes d1", "alexia.falcoz@gmail.com", "123456", lac1);
            Customer c2 = new Customer("c2","Laurine", "Rolland", "Parnell d1", "laurine.rolland@gmail.com", "789101", lac2);
            Customer c3 = new Customer("c3","Pablo", "Targa", "Docklands d2", "pablo.targa@gmail.com", "112131", lac3);

            allCustomers.add(c1);
            allCustomers.add(c2);
            allCustomers.add(c3);
        }
        
        init = false;
        
    }
    
    public List<Customer> getAllCustomersDB() {
        return allCustomers;
    }
    
    public List<Account> getAllAccountsDB(){
        return allAccounts;
    }
    
    public void setAccountDB(Account a){
        allAccounts.add(a);
    }
    
    public List<Transaction> getAlltransactionsDB(){
        return allTransactions;
    }
    
    public void settransactionDB(Transaction t){
        allTransactions.add(t);
    }
    
}
