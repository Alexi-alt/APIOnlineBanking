/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankingproject.service;

import com.mycompany.onlinebankingproject.database.Database;
import com.mycompany.onlinebankingproject.model.Customer;
import java.util.List;

/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */
public class CustomerService {
    
    public static Database db = new Database();
    List<Customer> lc = db.getAllCustomersDB();
    
    public Database getDatabase(){
        return db;
    }
    
    // retrieve all customers from DB
    public List<Customer> getAllCustomersDB(){
        return db.getAllCustomersDB();
    }
    
    // retrieve single customer based on customerID and password (for validation)
    public Customer getCustomerByIDDB(String customerID, String password){
        Customer cmatch = new Customer();
        for(Customer c : lc){
            if(customerID.equalsIgnoreCase(c.getCustomerID()) && password.equalsIgnoreCase(c.getCustomerPassword())){
                cmatch = c;
            }
        }
        return cmatch;
    }
}
