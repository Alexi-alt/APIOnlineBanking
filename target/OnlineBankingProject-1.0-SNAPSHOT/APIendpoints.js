
customerIDGlobal = '';
password = '';
accountForTransaction = '';

//function to clear customer info when changing customer or logging out
function clearCustomerInfo(){
    $('.resp-customer').html('');
    clearAccountInfo();
}

//function to clear account info when navigating back to accounts or to another customer
function clearAccountInfo(){
    $('.resp-showaccounts').html('');
    $('.createaccount').html('');
    $('.resp-accountdetails').html('');
    $('.resp-balance').html('');
    $('.resp-singleaccount').html('');
    $('.transactions').html('');
    clearTransactionInfo();
}

//function to clear transaction info zhen navigating to another account, back to the accounts or another customer
function clearTransactionInfo(){
    $('.resp-showtransactions').html('');
    $('.resp-single-transaction').html('');
    $('.createtransaction').html('');
    $('.transactiontable').html('');
}

//function to logout from the website, click button in the html form
function logout(){
    $("#customerID").val('');
    $("#password").val('');
    clearCustomerInfo();
}

//function to show the customer information
function showCustomer() {
    
    //this will automatically clear all customer information previously accessed
    clearCustomerInfo();
    
    //values retrieved from the form submission in the html - customer id and password
    var cid = document.getElementById('customerID').value;
    var pw = document.getElementById('password').value;
    
    //assigning values to global variable in to continue navigating through the functions with the same customer logged in
    customerIDGlobal = cid;
    password = pw;
    
    if(cid == ''){
        alert('Please enter your customer ID.');
        return;
    }
    
    if(pw == ''){
        alert('Please enter password.');
        return;
    }
    showCustomerInfo(customerIDGlobal);
}

function showCustomerInfo(customerIDGlobal){
    
    //creating the URL to GET customer info based on the API endpoint
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password;
    
    $.ajax({
        type: "get",
        url: url
    }).then(function (data) {
        $("#customerID").val('');
        $("#password").val('');
        
        //if customerID doesn't exist
        if(data.customerID == null){
            alert('Incorrect Customer ID or password');
            return;
        }
        
        //showing customer information

        //showing button to access the accounts and button to create a new account
        $('.resp-customer').html(
                '<div class="row p-4"><h2 class="col text-center pt-4">Welcome, ' + data.customerFirstName + ' ' + data.customerLastName 
                + '!</h2> <div class="col text-right"><h5>' + data.customerEmail 
                +'<h5>Customer ID: ' + data.customerID
                + '</h5><h5> Accounts: ' + data.customerAccounts.length 
                + '</h5></div></div><div class="d-flex justify-content-center">\n\
                <button class="viewaccounts btn btn-primary mx-3" type="submit">View your accounts</button>\n\
                <button class="precreateaccount btn btn-success mx-3" type="submit">Create a new account</button><div>');
    
        //function on click from button to show the accounts
        $('.viewaccounts').on("click", function() {
            showAccounts();
        });
        
        //function on click from button to create a new account 
        $('.precreateaccount').on("click", function() {
            clearAccountInfo();
        
            // if the button is clicked, creating an html form to submit account info
            $('.createaccount').html('');
            var createaccountcard = "<div class='form-group m-4'>\n\
            <p>Please select the account type and enter your branch number to create your account: </p>\n\
            <div class='form-check m-3'><input class='form-check-input' type='radio' id='accounttype' name='createaccount' value='current'><label class='form-check-label' for='current'>Current account</label></div>\n\
            <div class='form-check m-3'><input class='form-check-input' type='radio' id='accounttype' name='createaccount' value='savings'><label class='form-check-label' for='savings'>Savings account</label></div>\n\
            <div class='my-3 col-5'><label>Branch number: </label><input class='form-control' type='text' size='20' id='accountsortcode' name='accountsortcode' placeholder = 'abc123...'/></div>\n\
            <input class='btn btn-success m-3' type='button' onclick='createAccount();' value='Create'/><div>";
            $('.createaccount').append(createaccountcard);
        })
        
        //at the end of the show function customer function, clear customer info in the form
        $("#customerID").val('');
        $("#password").val('');
    });
};

//function to show the entire list of accounts
function showAccounts() {
    
    //if account entered, clear account info
    clearAccountInfo();
    
    //constructing the URL to GET all accounts info with global variable stored in show customer function
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts";
    $.ajax({
        type: "get",
        url: url
    }).then(function (accounts) {
        //for each account returned, display for each its information (function show account list)
        $(accounts).each(function (i, account){
            showAccountList(account);
        });
    });
};

function showAccountList(account){
    
    //what information to display for each account in the list
    var content = $(
            '<div class="card m-4" style="width: 18rem;">\n\
            <div class="card-body"><h5 class="card-title">Account ID: ' + account.accountNumber 
            + "</h5><p class='card-text'>Type: " + account.accountType 
            + "<br/> Sort code: " + account.sortCode 
            + "<br/> <p>" + account.currentBalance
            + " Euros </p><button class='viewsingleaccount btn btn-primary' type='submit'>Enter account</button></div>");
    
    //append the content in the html
    $('.resp-showaccounts').append(content);
    
    //button to enter the account
    content.find(".viewsingleaccount").click(function() {
        clearAccountInfo();
        showSingleAccount(account.accountNumber);
    });
}

//function to enter a single account
function showSingleAccount(accountNumber) {
    //if other account entered, then clear
    clearAccountInfo();
    
    accountForTransaction = accountNumber;
    
    //construct URL to GET single account information
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction;
    $.ajax({
        type: "get",
        url: url
    }).then(function (data) {
        //show single account information
        $('.resp-singleaccount').html(
                "<div class='card m-3 py-3 px-5'><div class='row'><div class='col'>Account ID: " + data.accountNumber 
                + "<br/>Account type: " + data.accountType 
                + "<br/>Sort code: " + data.sortCode 
                + "</div><div class='col text-right pt-3'><h2>" + data.currentBalance 
                + " Euros </h2></div></div>");
        
        //displaying buttons to show all transactions or create new transactions
        var content = "<div><ul id='tabs' class='nav nav-tabs' role='tablist'>\n\
        <li class='nav-item nav-link active'><a id='allaccounttransactions' href='#'>Account Transactions</a></li> \n\
        <li class='nav-item nav-link'><a id='debit' href='#'>Withdraw money</a></li> \n\
        <li class='nav-item nav-link'><a id='credit' href='#'>Lodge money</a></li>\n\
        <li class='nav-item nav-link'><a id='transfer' href='#'>Transfer money</a></li></ul>";
        
        $('.transactions').html(content);

        showTransactions(accountForTransaction);
        $('.nav-tabs a').click(function(){
            clearTransactionInfo();

            if($(this).attr('id')=="allaccounttransactions"){
                showTransactions(accountForTransaction);
            }
            if($(this).attr('id')=="debit"){
                precreateDebit();
            }
            if($(this).attr('id')=="credit"){
                precreateCredit();
            }
            if($(this).attr('id')=="transfer"){
                precreateTransfer();
            }
        });
        
    });
};

//function to create a new account
function createAccount(){
    
    //get the values of type of account and sort code from the form submission
    var accountsortcode = document.getElementById('accountsortcode').value;
    var accounttype = $("input:radio[name=createaccount]:checked").val();
    
    if(accounttype == null){
        alert('Please select an account type');
        return;
    }
    
    if(accountsortcode == ''){
        alert('Please enter your branch number');
        return;
    }
    
    //contruct URL to POST new account in the database
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/create/" + accounttype + "/" + accountsortcode;
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        //if function success or fail, then display an alert
        success: function(newAccount){
            var newA = newAccount;
            alert('Your new account number ' + newA.accountNumber + ' has been created!');
            showCustomerInfo(customerIDGlobal);
            showAccounts();
        }, error: function(){
            alert('There was an error in creating your new account.');
        }
    });
}


//functon to show all transactions for a specified account
function showTransactions(accountForTransaction){
    
    //construct URL to GET all transactions for an account
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction + "/transactions";
    $.ajax({
        type: "get",
        url: url
    }).then(function (transactions) {
        $('.transactiontable').html('');
        var content = '<table class="table table-hover"><thead><tr><th scope="col">Date</th><th scope="col">Type</th><th scope="col">Amount</th><th scope="col">Post balance</th><th scope="col">Enter transaction</th></tr></thead><tbody>';
        $('.transactiontable').append(content);
        //if the transaction data has at least one record
        if (transactions.length){
            $(transactions).each (function (i, transaction) {
                showTransactionList(transaction);
            });
        //if not, no transaction
        } else {
            alert('There is no transactions recorded for this account');
        }
        $('.transactiontable').after('</tbody></table>');
    }); 
}

//function to display each transaction information in the list
function showTransactionList(transaction){
    
    //each transaction information displayed in the list
    var content = $('<tr class="table-primary"><th scope="row" style="vertical-align: middle;"><small>'+ transaction.transDate 
            +'</small></th><td style="vertical-align: middle;">' + transaction.transType 
            +'</td><td style="vertical-align: middle;">' + transaction.transAmount
            +'</td><td style="vertical-align: middle;">' + transaction.postTransAccountBalance
            +'</td><td style="vertical-align: middle;"><button class="viewsingletransaction btn btn-primary" type="button" action="submit">Enter Transaction</a></td></tr>');
    //for each transaction in the list, append the content
    $('.transactiontable tr:last').after(content);
    
    //function to view a single transaction
    content.find('.viewsingletransaction').on("click", function() {
        showSingleTransaction(transaction.transID);
    });
}

//function to see a single transaction
function showSingleTransaction(transactionID){
    $('.transactiontable').html('');
    //construct URL to GET a single transaction
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction + "/transactions/" + transactionID;
    $.ajax({
        type: "get",
        url: url
    }).then(function (data) {
    
    //display the transaction information in the html
        // $('.resp-showtransactions').html('');
        $('.resp-single-transaction').html(
            "<div class='card mx-3 py-3 px-5'><div class='row'><div class='col'>Transaction ID: " + data.transID
            + "<br/>Transaction date: " + data.transDate 
            + "<br/>Transaction type: " + data.transType 
            + "<br/>Transaction description: " + data.transDescription
            + "<br/>Balance post transaction : " + data.postTransAccountBalance 
            +" Euros </div><div class='col text-right pt-3'><small>Transaction amount: </small><br/><h3>" + data.transAmount + " Euros</h3></div></div></div>\n\
            <div class='text-center mt-3'><button class='back-to-all-trans btn btn-primary' type='button' action='submit'>Back to all transactions</button></div>");
        
        $('.back-to-all-trans').on("click", function() {
            $('.resp-single-transaction').html('');
            showTransactions(accountForTransaction);
        });
        
    });
}

//function to display the form to create a debit
function precreateDebit(){
    $('.createtransaction').html('');
    
    //form creation with input to create a new debit: amount and description
    var createtransactioncard = "<div class='form-group m-4 pr-2'>\n\
    <p>Please enter the amount you'd like to withdraw and a description: </p>\n\
    <div class='form-check my-3'><label>Withdraw amount: </label><input class='form-control' type='number' id='transactionamount' name='transactionamount' placeholder = '00.00'/></div>\n\
    <div class='form-check my-3'><label>Withdraw description: </label><input class='form-control' type='text' id='transactiondescription' name='transactiondescription' placeholder = 'Shopping...'/></div>\n\
    <div class='text-center'><input class='btn btn-success m-3' type='button' onclick='createDebit();' value='Submit this withdrawal'/></div></div>";
    
    $('.createtransaction').html(createtransactioncard);
    
}

//function to create a debit for the account
function createDebit(){
    
    //retrieving the values entered in the form: amount and description
    var amount = document.getElementById('transactionamount').value;
    var description = document.getElementById('transactiondescription').value;
    
    if(amount == '' || amount == 0){
        alert('Please enter an amount');
        return;
    }

    if(description == ''){
        alert('Please enter a description');
        return;
    }

    //construct URL to POST a new debit transaction
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction + "/transactions/withdrawal/" + amount + "&" + description;
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        
        //if success or fail, display an alert
        success: function(transaction){
            alert("Transaction number " + transaction.transID + " for withdrawing " + transaction.transAmount + " euros, has been completed!");
            showSingleAccount(accountForTransaction);
            showTransactions(accountForTransaction);
        }, error: function(){
            alert('There was an error in creating the transation.')
        }
    });
}


//function to display the form to create a credit
function precreateCredit(){
    $('.createtransaction').html('');
    
    //form creation with input to create a new credit: amount and description
    var createtransactioncard = "<div class='form-group m-4 pr-2'>\n\
    <p>Please enter the amount you'd like to lodge and a description: </p>\n\
    <div class='form-check my-3'><label>Lodgement amount: </label><input class='form-control' type='number' id='transactionamount' name='transactionamount' placeholder = '00.00'/></div>\n\
    <div class='form-check my-3'><label>Lodgement description: </label><input class='form-control' type='text' id='transactiondescription' name='transactiondescription' placeholder = 'Refund...'/></div>\n\
    <div class='text-center'><input class='btn btn-success m-3' type='button' onclick='createCredit();' value='Submit this lodgement'/></div>";
    
    $('.createtransaction').html(createtransactioncard);    
    
}

//function to create a credit for the account
function createCredit(){
    
    //retrieving the values entered in the form: amount and description
    var amount = document.getElementById('transactionamount').value;
    var description = document.getElementById('transactiondescription').value;

    if(amount == '' || amount == 0){
        alert('Please enter an amount');
        return;
    }

    if(description == ''){
        alert('Please enter a description');
        return;
    }

    //construct URL to POST a new credit transaction
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction + "/transactions/lodgement/" + amount + "&" + description;
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        
        //if success or fail, display an alert
        success: function(transaction){
            alert("Transaction number " + transaction.transID + " for lodging " + transaction.transAmount + " euros, has been completed!");
            showSingleAccount(accountForTransaction);
            showTransactions(accountForTransaction);
        }, error: function(){
            alert('There was an error in creating the transation.');
        }
    });
}

//function to display the form to create a transfer
function precreateTransfer(){
    $('.createtransaction').html('');
    
    //form creation with input to create a new transfer: amount, account destination and description
    var createtransactioncard = "<div class='form-group m-4 pr-2'>\n\
    <p>Please enter the amount you'd like to transfer, the account number destination and a description: </p>\n\
    <div class='form-check my-3'><label>Transfer amount: </label><input class='form-control' type='number' id='transactionamount' name='transactionamount' placeholder = '00.00'/></div>\n\
    <div class='form-check my-3'><label>Account number: </label><input class='form-control' type='text' id='accountdestination' name='accountdestination' placeholder = 'abc123..'/></div>\n\
    <div class='form-check my-3'><label>Transfer description: </label><input class='form-control' type='text' id='transactiondescription' name='transactiondescription' placeholder = 'Bill...'/></div>\n\
    <div class='text-center'><input class='btn btn-success m-3' type='button' onclick='createTransfer();' value='Submit this transfer'/></div>";
    
    $('.createtransaction').html(createtransactioncard);    
    
}

//function to create a transfer for the account
function createTransfer(){
    
    //retrieving the values entered in the form: amount, account destination and description
    var amount = document.getElementById('transactionamount').value;
    var accountdestination = document.getElementById('accountdestination').value;
    var description = document.getElementById('transactiondescription').value;
    
    if(amount == '' || amount == 0){
        alert('Please enter an amount');
        return;
    }
    
    if(accountdestination == ''){
        alert('Please enter an account destination');
        return;
    }

    if(description == ''){
        alert('Please enter a description');
        return;
    }

    //construct URL to POST a new transfer transaction
    var url = "http://localhost:49000/onlinebanking/api/customers/" + customerIDGlobal + "/" + password + "/accounts/" + accountForTransaction + "/transactions/transfer/" + accountdestination + "/" + amount + "&" + description;
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        
        //if success or fail, display an alert
        success: function(transaction){
            if(transaction.transAmount == 0){
                alert('Incorrect account destination, please verify account destination number.');
            } else {
                alert("Transaction number " + transaction.transID + " for transferring " + transaction.transAmount + " euros, has been completed!");
                showSingleAccount(accountForTransaction);
                showTransactions(accountForTransaction);
            }
        }, error: function(){
            alert('There was an error in creating the transation.');
        }
    });
}
