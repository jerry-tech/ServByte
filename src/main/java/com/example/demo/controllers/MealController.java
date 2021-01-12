package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.Meal;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.MealRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.service.Logic;
import com.example.demo.service.MealStorageService;
import com.example.demo.service.ResponseHandler;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MealController {

    final
    Logic logic;

    private static final Logger logger = LoggerFactory.getLogger(Meal.class);

    public MealController(Logic logic) {
        this.logic = logic;
    }

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MealStorageService mealStorageService;

    /**
     * method used to create a meal for restaurants
     */
    @PostMapping("/api/v1/meal")
    public ResponseEntity<?> createMeal(@RequestHeader("Authorization") String JWT, @RequestParam("picture") MultipartFile picture, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") BigDecimal price, @RequestParam("timeTaken") String timeTaken) {
        // getting accountId from Json web token
        Long accountId = logic.returnId(JWT);

        //getting user restaurant details using the user accountId
        Restaurant restaurant = restaurantRepository.getRestaurantByAccount(accountId);

        try {
            //passing data through the overloaded constructor of the meal class
            Meal meal = new Meal(name, mealStorageService.storeFile(picture), price, timeTaken, description, new Restaurant(restaurant.getRestId()));
            Meal mealResponse = mealRepository.saveAndFlush(meal);//saving meal

            return responseHandler.generateResponse(HttpStatus.OK, true, "Meal account created successfully.", mealResponse);

        } catch (Exception e) {
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), null);
        }

    }

    /**method used to all meal by a restaurant */
    @GetMapping("/api/v1/getMeal")
    public ResponseEntity<?> getAllMealByRestaurant(@RequestParam("restId")Long restId){

        try{
            //getting all meal by a restaurant
            List<Meal> mealList = mealRepository.findAll()
                    .stream()
                    .filter(c-> c.getRestaurant().getRestId().equals(restId))
                    .collect(Collectors.toList());

            return responseHandler.generateResponse(HttpStatus.OK, true, "All meal by a single restaurant.", mealList);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unable to get single meal.", null);
        }
    }

    /**method used to get a single meal */
    @GetMapping("/api/v1/getMeal/{mealId}")
    public ResponseEntity<?> getSingleMeal(@PathVariable("mealId")Long mealId){

        try{
            //calling the getOne method of JPA repository
            Meal meal = mealRepository.getOne(mealId);

            return responseHandler.generateResponse(HttpStatus.OK, true, "Getting single meal successful", meal);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unable to get single meal.", null);
        }
    }

    //used for successful image download
    @GetMapping("/meal/{fileName:.+}")
    public ResponseEntity<Resource> downloadMealFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = mealStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
