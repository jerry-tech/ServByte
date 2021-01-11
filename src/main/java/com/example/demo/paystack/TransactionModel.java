package com.example.demo.paystack;

public class TransactionModel {

    private String reference;

    /**
     * note that amount must be in kobo
     */
    private String amount;

    /**
     * email of the payer
     */
    private String email;

    /**
     * Url for redirecting after a successful payment
     */
    private String callback_url;


    public TransactionModel(String reference, String amount, String email, String callback_url) {
        this.reference = reference;
        this.amount = amount;
        this.email = email;
        this.callback_url = callback_url;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }
}