package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseHandler {

    public ResponseEntity<Object> generateResponse(HttpStatus status, boolean success, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", success);
            map.put("message", message);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public ResponseEntity<Object> generateMapResponse(HttpStatus status, boolean success, String message, JSONObject responseObj) {
        Map<String, Object> response = responseObj.toMap();
        response.put("status", status.value());
        response.put("isSuccess", success);
        response.put("message", message);

        return new ResponseEntity<Object>(response, status);
    }
}
