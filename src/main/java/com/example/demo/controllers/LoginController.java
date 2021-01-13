package com.example.demo.controllers;


import com.example.demo.jwt.JsonWebToken;
import com.example.demo.models.Account;
import com.example.demo.models.Login;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired//MyUserDetails service
    private MyUserDetailService userDetailsService;

    @Autowired//Json web token
    private JsonWebToken jsonWebToken;

    @Autowired//loginRepository interface
    private AccountRepository accountRepository;

    @Autowired//Autowiring the response handler for the entity response handler
    private ResponseHandler responseHandler;

    //declaring the variable for the assigning the JWT value.
    String JWT = null;

    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)//@PostMapping
    public ResponseEntity<?> accountLogin(@RequestBody Login login) {

        //loading user credentials by users inputted username
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getName().toLowerCase());

        try {

            //using method in the account repository to get other users credentials
            Account account = accountRepository.getByName(login.getName().toLowerCase());

            //instantiating the BCryptPasswordEncoder class
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            //comparing the user inputted password and the hashed password in the db
            boolean isMatches = passwordEncoder.matches(login.getPassword(), userDetails.getPassword());

            if (isMatches && userDetails.getUsername().equalsIgnoreCase(login.getName()) && login.getAccountType().equalsIgnoreCase(account.getAccountType())) {
                //using the userDetails to generate the JSon Web Token
                JWT = jsonWebToken.generateToken(userDetails);

                Map<String, Object> response = new HashMap<>();
                response.put("accountId", account.getAccountId());
                response.put("JWT", JWT);
                response.put("username", account.getName().toLowerCase());
                response.put("accountType", account.getAccountType().toLowerCase());
                response.put("emailAddress", account.getEmailAddress());
                response.put("phoneNumber", account.getPhoneNumber());


                //building custom success response
                return responseHandler.generateResponse(HttpStatus.OK, true, "Login Successful.", response);
            } else {
                //building custom error response
                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Invalid Username or Password", new HashMap<>());
            }
        } catch (Exception e) {
            //building custom error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Invalid Username or Password", new HashMap<>());
        }

    }

}
