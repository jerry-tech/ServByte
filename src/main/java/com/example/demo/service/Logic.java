package com.example.demo.service;

import com.example.demo.jwt.JsonWebToken;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class Logic {

    @Autowired
    JsonWebToken jsonWebToken;

    @Autowired
    AccountRepository accountRepository;

    //method used to return the Id of user from the Json web token
    public Long returnId(String JWT) {

        //extracting username from jsonWebToken
        String name = jsonWebToken.extractUsername(JWT);

        //using the username to get the user's Id
        return accountRepository.getByName(name).getAccountId();
    }

    //method used for order no generation
    public String getOrderNoGeneration() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 64; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 64))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    //method used to build email message template
    public String emailBody(String username,  String mealName, String deliveryTime) {
        return "Hi "+username+"!. We’re so excited that you’ve decided to order "+mealName+".\n" +
                "Don't you worry you meal will be at you door step at approximately "+ deliveryTime +"\n" +
                "Thanks for choosing ServeByte Inc.";
    }

    //method used to generate random used for the otp
    public int getOtpCode() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();

        return rnd.nextInt(999999);
    }


}
