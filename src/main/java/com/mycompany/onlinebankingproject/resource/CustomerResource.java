/**
 *
 * @author Alexia Falcoz & Laurine Rolland
 */

package com.mycompany.onlinebankingproject.resource;

import com.mycompany.onlinebankingproject.model.Customer;
import com.mycompany.onlinebankingproject.service.CustomerService;
import javax.xml.bind.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")

public class CustomerResource {
    
    CustomerService cs = new CustomerService();
    
    //GET single customer information
    @GET
    @Path("/{customerID}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerInfo(@PathParam("customerID") String customerID, @PathParam("password") String password){
        Customer c = cs.getCustomerByIDDB(customerID, password);
        return Response.ok(c).header("Access-Control-Allow-Origin", "*").build();
    }
    
    //Access Account subresource
    @Path("/{customerID}/{password}/accounts")
    public AccountResource getAccountResource(){
        return new AccountResource();
    }
    
}
