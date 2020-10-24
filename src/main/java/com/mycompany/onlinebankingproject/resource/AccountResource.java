/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */

package com.mycompany.onlinebankingproject.resource;

import com.mycompany.onlinebankingproject.model.Account;
import com.mycompany.onlinebankingproject.service.AccountService;
import java.util.List;
import javax.xml.bind.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")

public class AccountResource {
    
    AccountService as = new AccountService();
    
    //GET all accounts per customer
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountsPerCustomer(@PathParam("customerID") String customerID, @PathParam("password") String password){
        List<Account> allaccounts = as.getAllAccountsPerCustomerDB(customerID, password);
        GenericEntity<List<Account>> entity = new GenericEntity<List<Account>>(allaccounts) {};
        return Response.ok(entity).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //GET single account
    @GET
    @Path("/{accountID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountPerID(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID){
        Account a = as.getAccountByIDDB(customerID, password, accountID);
        return Response.ok(a).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //POST a new current account
    @POST
    @Path("/create/current/{sortCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCurrentAccount(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("sortCode") String sortCode){
        Account a = as.createAccountDB(customerID, password, "current", sortCode);
        return Response.ok(a).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //POST a new savings account
    @POST
    @Path("/create/savings/{sortCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSavingsAccount(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("sortCode") String sortCode){
        Account a = as.createAccountDB(customerID, password, "savings", sortCode);
        return Response.ok(a).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //Access Transactions subresource
    @Path("/{accountID}/transactions")
    public TransactionResource getTransactionResource(){
        return new TransactionResource();
    }
    
}
