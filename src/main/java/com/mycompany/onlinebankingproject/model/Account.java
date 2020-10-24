/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */

package com.mycompany.onlinebankingproject.model;

import java.util.List;

public class Account {
    
    private String accountID;
    private String accountType;
    private String sortCode;
    private double currentBalance;
    private List<Transaction> accountTransactions;
    
    public Account(){
    }

    public Account(String accountID, String accountType, String sortCode, double currentBalance, List<Transaction> accountTransactions) {
        this.accountID = accountID;
        this.accountType = accountType;
        this.sortCode = sortCode;
        this.currentBalance = currentBalance;
        this.accountTransactions = accountTransactions;
    }
        
    public String getAccountNumber() {
        return accountID;
    }

    public void setAccountNumber(String accountID) {
        this.accountID = accountID;
    }
    
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public List<Transaction> getTransactions() {
        return accountTransactions;
    }

    public void setTransactions(List<Transaction> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }
    
    
    
}
