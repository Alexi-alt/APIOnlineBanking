/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */

package com.mycompany.onlinebankingproject.model;

import java.util.Date;

public class Transaction {
    
    private String transID;
    private String transType; //either a credit or a debit
    private Date transDate;
    private double transAmount;
    private String transDescription;
    private double postTransAccountBalance;
    
    public Transaction(){
        
    }

    public Transaction(String transID, String transType, double transAmount, String transDescription, double postTransAccountBalance) {
        this.transID = transID;
        this.transType = transType;
        this.transDate = new Date();
        this.transAmount = transAmount;
        this.transDescription = transDescription;
        this.postTransAccountBalance = postTransAccountBalance;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }
    
    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }
    
    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }
    
    public double getPostTransAccountBalance() {
        return postTransAccountBalance;
    }

    public void setPostTransAccountBalance(double postTransAccountBalance) {
        this.postTransAccountBalance = postTransAccountBalance;
    }
    
}
