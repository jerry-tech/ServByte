package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.Delivery;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DeliveryStorageService deliveryStorageService;

    @Autowired
    RestStorageService restStorageService;

    @Autowired
    Logic logic;

    @Autowired
    EncodePassword encodePassword;

    @PostMapping("/delivery")
    /**method used to create an account for delivery businesses */
    public ResponseEntity<?> createAccDelivery(@RequestParam(value = "bike", required = false) String bike, @RequestParam(value = "boat", required = false) String boat, @RequestParam(value = "car", required = false) String car, @RequestParam("logo") MultipartFile logo, @RequestParam(value = "timeBike", required = false) String timeBike, @RequestParam(value = "timeBoat", required = false) String timeBoat, @RequestParam(value = "timeCar", required = false) String timeCar, @RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {

        try {
            //passing data through the constructor of the account class
            Account account = new Account(name.toLowerCase(), emailAddress, phoneNumber, encodePassword.encryptPassword(password), "delivery");
            accountRepository.saveAndFlush(account);

            try {
                Delivery deliveryCont = new Delivery(deliveryStorageService.storeFile(logo), Long.valueOf(bike), Long.valueOf(car), Long.valueOf(boat), timeBoat, timeCar, timeBike, new Account(account.getAccountId()));
                //saving delivery details;
                Delivery deliveryResponse = deliveryRepository.saveAndFlush(deliveryCont);

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
                Restaurant restConst = new Restaurant(restStorageService.storeFile(logo), city, new Account(account.getAccountId()));
                //saving restaurant details;
                Restaurant restResponse = restaurantRepository.saveAndFlush(restConst);

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
    public ResponseEntity<?> createAccRestaurant(@RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {

        try {
            //passing data through the constructor of the account class
            Account account = new Account(name.toLowerCase(), emailAddress, phoneNumber, encodePassword.encryptPassword(password), "user");
            accountRepository.saveAndFlush(account);//saving account data

            return responseHandler.generateResponse(HttpStatus.OK, true, "User account created successfully.", account);

        } catch (Exception e) {
            //error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in creating account." + e.getMessage(), null);
        }
    }

    @GetMapping("/getDelivery")
    public ResponseEntity<?> createAccRestaurant() {

        try {
            List<Account> accountResponse = accountRepository.findAll().stream().filter(c->c.getAccountType().equalsIgnoreCase("delivery")).collect(Collectors.toList());;
            return responseHandler.generateResponse(HttpStatus.OK, true, "successfully.", accountResponse);

        } catch (Exception e) {
            //error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "." + e.getMessage(), null);
        }
    }




}
