/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */

package com.mycompany.onlinebankingproject.resource;

import com.mycompany.onlinebankingproject.model.Transaction;
import com.mycompany.onlinebankingproject.service.TransactionService;
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

@Path("/transactions")
public class TransactionResource {
    
    TransactionService ts = new TransactionService();
    
    //GET all transactions per account
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransationsPerAccount(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID){
        List<Transaction> t = ts.getAllTransactionsPerAccountDB(customerID, password, accountID);
        GenericEntity<List<Transaction>> entity = new GenericEntity<List<Transaction>>(t) {};
        return Response.ok(entity).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //GET single transaction
    @GET
    @Path("/{transactionID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID, @PathParam("transactionID") String transactionID){
        Transaction t = ts.getTransactionByIDDB(customerID, password, accountID, transactionID);
        return Response.ok(t).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //POST new debit transaction
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/withdrawal/{amount}&{description}")
    public Response createWithdrawal(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID, @PathParam("amount") double amount, @PathParam("description") String description){
        Transaction t = ts.createWithdrawalDB(customerID, password, accountID, amount, description);
        return Response.ok(t).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //POST new credit transaction
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/lodgement/{amount}&{description}")
    public Response createLodgement(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID, @PathParam("amount") double amount, @PathParam("description") String description){
        Transaction t = ts.createLodgementDB(customerID, password, accountID, amount, description);
        return Response.ok(t).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //POST new transfer transaction
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transfer/{accountDestination}/{amount}&{description}")
    public Response createTransfer(@PathParam("customerID") String customerID, @PathParam("password") String password, @PathParam("accountID") String accountID, @PathParam("accountDestination") String accountDestination, @PathParam("amount") double amountt, @PathParam("description") String description){
        Transaction t = ts.createTransferDB(customerID, password, accountID, accountDestination, amountt, description);
        return Response.ok(t).header("Access-Control-Allow-Origin", "*").build();
    }
    
}
