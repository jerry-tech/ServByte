package com.example.demo.twilio;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    private String account_sid =  "ACb5408a62923d07ed31d01de43706d66d";
    private String auth_token = "0712df4d9916171a93de13eff712e359";
    private String trial_number = "+12018905763";

    public TwilioConfiguration(){

    }

    public String getAccount_sid() {
        return account_sid;
    }

    public void setAccount_sid(String account_sid) {
        this.account_sid = account_sid;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getTrial_number() {
        return trial_number;
    }

    public void setTrial_number(String trial_number) {
        this.trial_number = trial_number;
    }
}
