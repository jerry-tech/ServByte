package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.Delivery;
import com.example.demo.models.Meal;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.service.DeliveryStorageService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    DeliveryStorageService deliveryStorageService;

    private static final Logger logger = LoggerFactory.getLogger(Delivery.class);

    /**
     * method used to get all delivery companies
     */
    @GetMapping("/api/v1/delivery/{name}")
    public ResponseEntity<?> getDelivery(@PathVariable("name")String name){
        try{
            //calling the getOne method of JPA repository
            Delivery delivery = deliveryRepository.getDeliveryAccount(name);

            return responseHandler.generateResponse(HttpStatus.OK, true, "All delivery company", delivery);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unable to get single meal.", null);
        }
    }

    //used for successful image download
    @GetMapping("/delivery/{fileName:.+}")
    public ResponseEntity<Resource> downloadDeliveryPic(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = deliveryStorageService.loadFileAsResource(fileName);

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
