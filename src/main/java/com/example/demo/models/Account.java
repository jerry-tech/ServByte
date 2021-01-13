package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "account")//defining table name
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="account_id", unique = true)
    private Long accountId;

    @Column(name="name", nullable=false, length=50, unique = true)
    private String name;//unique

    @Column(name="email_address", nullable=false, length=50)
    private String emailAddress;

    @Column(name="phone_number", nullable=false, length= 20)
    private String phoneNumber;

    @Column(name="password", nullable=false, length=80)
    private String password;

    @Column(name="account_type", nullable=false, length=50)
    private String accountType;

    public Account(){

    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account(String name, String emailAddress, String phoneNumber, String password, String accountType) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountType = accountType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

