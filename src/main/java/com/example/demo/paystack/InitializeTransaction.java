package com.example.demo.paystack;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InitializeTransaction {

    //for the connecting the web
    private ApiConnection apiConnection;

    /**
     * Used to initialize a transaction
     *
     * @param reference
     * @param amount
     * @param email
     * @param plan
     * @param callback_url
     * @return
     */
    public JSONObject initializeTransaction(String reference, String amount, String email,
                                            String plan, String callback_url) {
        this.apiConnection = new ApiConnection(Definitions.PAYSTACK_TRANSACTIONS_INITIALIZE_TRANSACTION);
        ApiQuery apiQuery = new ApiQuery();
        apiQuery.putParams("reference", reference);
        apiQuery.putParams("amount", amount);
        apiQuery.putParams("email", email);
        apiQuery.putParams("plan", plan);
        apiQuery.putParams("callback_url", callback_url);

        return this.apiConnection.connectAndQuery(apiQuery);
    }

    /**
     * used to verify transactions
     * @param reference
     */
    public JSONObject verifyTransaction(String reference){
        this.apiConnection = new ApiConnection("https://api.paystack.co/transaction/verify/"+ reference);

        return this.apiConnection.connectAndQueryWithGet();
    }
}
