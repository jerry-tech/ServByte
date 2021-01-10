package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.Delivery;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.service.Logic;
import com.example.demo.service.EncodePassword;
import com.example.demo.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    Logic logic;

    @Autowired
    EncodePassword encodePassword;

    @PostMapping("/delivery")
    /**method used to create an account for delivery businesses */
    public ResponseEntity<?> createAccDelivery(@RequestParam("bike") int bike, @RequestParam("boat") int boat, @RequestParam("car") int car, @RequestParam("logo") MultipartFile logo, @RequestParam("timeBike") String timeBike, @RequestParam("timeBoat") String timeBoat, @RequestParam("timeCar") String timeCar, @RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {

        try {
            //passing data through the constructor of the account class
            Account account = new Account(name.toLowerCase(), emailAddress, phoneNumber, encodePassword.encryptPassword(password), "delivery");
            accountRepository.saveAndFlush(account);

            try {
                Delivery deliveryCont = new Delivery(logic.compressBytes(logo.getBytes()), bike, car, boat, timeBoat, timeCar, timeBike, new Account(account.getAccountId()));
                //saving delivery details;
                Delivery deliveryResponse = deliveryRepository.saveAndFlush(deliveryCont);
                deliveryResponse.setLogo(null);

                return responseHandler.generateResponse(HttpStatus.OK, true, "Delivery Company account created successfully.", deliveryResponse);
            } catch (Exception e) {

                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Error in saving delivery details." + e.getMessage(), null);
            }

        } catch (Exception e) {

            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in creating account." + e.getMessage(), null);
        }
    }

    @PostMapping("/restaurant")
    /**method used to create an account for restaurant businesses */
    public ResponseEntity<?> createAccRestaurant(@RequestParam("city") String city, @RequestParam("logo") MultipartFile logo, @RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {

        try {
            //passing data through the constructor of the account class
            Account account = new Account(name.toLowerCase(), emailAddress, phoneNumber, encodePassword.encryptPassword(password), "restaurant");
            accountRepository.saveAndFlush(account);//saving account data

            try {
                Restaurant restConst = new Restaurant(logic.compressBytes(logo.getBytes()), city, new Account(account.getAccountId()));
                //saving restaurant details;
                Restaurant restResponse = restaurantRepository.saveAndFlush(restConst);
                restResponse.setLogo(null);

                return responseHandler.generateResponse(HttpStatus.OK, true, "Delivery Company account created successfully.", restResponse);
            } catch (Exception e) {
                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in creating account." + e.getMessage(), null);
            }
        } catch (Exception e) {

            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in creating account." + e.getMessage(), null);
        }
    }

    @PostMapping("/users")
    /**method used to create an account for users */
    public ResponseEntity<?> createAccRestaurant(@RequestBody Account account) {

        try {
            account.setName(account.getName().toLowerCase());//to lowercase
            account.setPassword(encodePassword.encryptPassword(account.getPassword()));//encoding password
            Account accountResponse = accountRepository.saveAndFlush(account);//saving account data

            return responseHandler.generateResponse(HttpStatus.OK, true, "User account created successfully.", accountResponse);

        } catch (Exception e) {
            //error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in creating account." + e.getMessage(), null);
        }
    }

}