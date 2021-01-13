package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.paystack.InitializeTransaction;
import com.example.demo.repository.*;
import com.example.demo.service.DefaultEmailService;
import com.example.demo.service.Logic;
import com.example.demo.service.ResponseHandler;
import com.example.demo.twilio.SmsRequest;
import com.example.demo.twilio.TwilioSmsSender;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    InitializeTransaction initializeTransaction;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    Logic logic;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DefaultEmailService defaultEmailService;

    @Autowired
    TwilioSmsSender twilioSmsSender;

    String transactionReference;

    private static final Logger logger = LoggerFactory.getLogger(Order.class);
    private String avgDeliveryTime;
    private String deliveryMeans;

    /**
     * method used to initiate payment of a meal in a restaurant using a delivery company
     */
    @PostMapping
    public ResponseEntity<?> mealPayOrder(@RequestHeader("Authorization") String JWT, @RequestParam("deliveryMeans") String deliveryMeans, @RequestParam("amount") String amount, @RequestParam("deliveryFee") String deliveryFee,  @RequestParam("mealId") Long mealId, @RequestParam("deliveryId") Long deliveryId) {
        //order no
        this.transactionReference = logic.getOrderNoGeneration();

        BigDecimal total = BigDecimal.valueOf(Long.parseLong(amount)).add(BigDecimal.valueOf(Long.parseLong(deliveryFee)));

        //getting user accountId from JWT
        Long accountId = logic.returnId(JWT);

        //using accountId to getUsersEmail
        String email = accountRepository.getOne(accountId).getEmailAddress();

        Long restId = mealRepository.getResturantByMeal(mealId).getRestaurant().getRestId();

        this.deliveryMeans = deliveryMeans;

        try {

            //passing the data coming from the transaction model into the initialize transaction method
            JSONObject jsonObject = initializeTransaction.initializeTransaction(this.transactionReference, total.toString(), email, null, null);

            //checking if transaction initialization is true
            if (jsonObject.getBoolean("status")) {
                try {

                    Order order = new Order(deliveryMeans, "pending", BigDecimal.valueOf(Long.parseLong(deliveryFee)), BigDecimal.valueOf(Long.parseLong(amount)), total, transactionReference, new Account(accountId), new Meal(mealId), new Restaurant(restId), new Delivery(deliveryId));
                    orderRepository.saveAndFlush(order);

                    //success response
                    return responseHandler.generateMapResponse(HttpStatus.OK, true, "Success in initializing payment.", jsonObject);

                } catch (Exception e) {

                    return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in saving payment details into database." + e, null);
                }
            } else {
                //error response
                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Payment Transaction not initiated.", null);
            }
        } catch (Exception e) {
            //error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error in Making payment." + e, null);
        }

    }

    @GetMapping("/verifyTransaction")
/** execute after a successful payment or error in payment */
    public Map<String, Object> getTransaction() {

        //used to get the info of a particular transaction
        JSONObject stringObjectMap = this.initializeTransaction.verifyTransaction(this.transactionReference);

        //checking if transaction is successful
        if (stringObjectMap.getJSONObject("data").getString("gateway_response").equals("Successful")) {
            try {
                //updating transaction details
                int stat = orderRepository.updateOrderByPayment("card", "https://api.paystack.co/transaction/verify/" + this.transactionReference, "confirmed", this.transactionReference);

                if (stat > 0) {
                    Order orders = orderRepository.getAllByAccount(transactionReference);

                    //getting account details
                    Account account = accountRepository.getOne(orders.getAccount().getAccountId());

                    Delivery delivery = deliveryRepository.getOne(orders.getDelivery().getDeliveryId());

                    if(this.deliveryMeans.equalsIgnoreCase("bike")){
                        this.avgDeliveryTime = delivery.getTimeBike();
                    }else if(this.deliveryMeans.equalsIgnoreCase("boat")){
                        this.avgDeliveryTime = delivery.getTimeBoat();
                    }else if(this.deliveryMeans.equalsIgnoreCase("car")){
                        this.avgDeliveryTime = delivery.getTimeCar();
                    }
                    //sending email
                    try {
                        defaultEmailService.sendSimpleEmail(account.getEmailAddress(), "Meal Order Successful.", logic.emailBody(account.getName(), mealRepository.getOne(orders.getMeal().getMealId()).getName(), this.avgDeliveryTime));

                        //sending sms to users upon a successful payment
                        //populating the constructor of the send sms twilio
                        SmsRequest smsRequestOTP = new SmsRequest(account.getPhoneNumber(), logic.emailBody(account.getName(), mealRepository.getOne(orders.getMeal().getMealId()).getName(), ""));
                        twilioSmsSender.sendSms(smsRequestOTP);

                        return stringObjectMap.toMap();
                    } catch (MailException mailException) {
                        logger.error("Error while sending out email..{}", mailException.getMessage());
                        return null;
                    }
                } else {
                    return null;
                }

            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                //updating transaction details
                orderRepository.updateOrderByPayment("card", "https://api.paystack.co/transaction/verify/" + this.transactionReference, "error", this.transactionReference);

                return stringObjectMap.toMap();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
