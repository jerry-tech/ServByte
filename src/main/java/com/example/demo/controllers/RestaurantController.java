package com.example.demo.controllers;

import com.example.demo.models.Delivery;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.service.DeliveryStorageService;
import com.example.demo.service.ResponseHandler;
import com.example.demo.service.RestStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    RestStorageService restStorageService;

    private static final Logger logger = LoggerFactory.getLogger(Restaurant.class);

    /**
     * method used to get all delivery companies
     */
    @GetMapping("/api/v1/restaurants")
    public ResponseEntity<?> getRestaurants(@RequestHeader("Authorization") String JWT){
        try{
            //calling the getOne method of JPA repository
            List<Restaurant> restaurants = restaurantRepository.findAll();

            return responseHandler.generateResponse(HttpStatus.OK, true, "All restaurant company", restaurants);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unable all restaurants.", null);
        }
    }

    //used for successful image download
    @GetMapping("/restaurants/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = restStorageService.loadFileAsResource(fileName);

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
