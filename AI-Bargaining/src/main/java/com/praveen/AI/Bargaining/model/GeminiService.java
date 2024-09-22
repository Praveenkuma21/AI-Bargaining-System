//
//package com.praveen.AI.Bargaining.model;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    private static final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s"; // Replace with actual URL
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public Map<String, Object> negotiate(Map<String, Object> product, Map<String, Object> negotiationParams) {
//        String geminiKey = "AIzaSyAlsxmZwmQBZoC12txD1tFcncgoBelizAg"; // Replace with actual key
//        String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);
//
//        // Create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Build JSON request body
//        ObjectNode contentNode = objectMapper.createObjectNode();
//        ObjectNode partsNode = objectMapper.createObjectNode();
//        
//        // Create a string that represents the product and negotiation summary
//        String productDescription = String.format(
//            "Product: %s, Description: %s, Price: %.2f, Quantity: %d", 
//            product.get("name"), 
//            product.get("description"), 
//            (Double) product.get("price"), 
//            (Integer) product.get("quantity")
//        );
//        
//        String negotiationSummary = String.format(
//            "Target Price: %.2f, Quantity Range: %s, Strategy: %s, Timeout: %d seconds",
//            (Double) negotiationParams.get("target_price"),
//            (String) negotiationParams.get("quantity_range"),
//            (String) negotiationParams.get("negotiation_strategy"),
//            (Integer) negotiationParams.get("timeout_seconds")
//        );
//
//        // Assuming the API expects some form of 'text' input for processing
//        partsNode.put("text", productDescription + ". " + negotiationSummary);
//        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
//
//        ObjectNode requestBodyNode = objectMapper.createObjectNode();
//        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));
//
//        // Convert the request body to a JSON string
//        String requestBody;
//        try {
//            requestBody = objectMapper.writeValueAsString(requestBodyNode);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to construct JSON request body", e);
//        }
//
//        // Create an HTTP entity with the request body and headers
//        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//
//        // Send the POST request to the Gemini API
//        try {
//            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);
//
//            // Return the response body as a map
//            return response.getBody();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to communicate with Gemini API", e);
//        }
//    }
//}


//package com.praveen.AI.Bargaining.model;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//@Service
//public class GeminiService {
//
//    private static final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s"; // Replace with actual URL
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public Map<String, Object> negotiate(Map<String, Object> product, Map<String, Object> negotiationParams) {
//        String geminiKey = "AIzaSyAlsxmZwmQBZoC12txD1tFcncgoBelizAg"; // Replace with actual key
//        String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);
//
//        // Create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Build JSON request body
//        ObjectNode contentNode = objectMapper.createObjectNode();
//        ObjectNode partsNode = objectMapper.createObjectNode();
//        
//        // Create a string that represents the product and negotiation summary
//        String productDescription = String.format(
//            "Product: %s, Description: %s, Price: %.2f, Quantity: %d", 
//            product.get("name"), 
//            product.get("description"), 
//            (Double) product.get("price"), 
//            (Integer) product.get("quantity")
//        );
//        
//        String negotiationSummary = String.format(
//            "Target Price: %.2f, Quantity Range: %s, Strategy: %s, Timeout: %d seconds",
//            (Double) negotiationParams.get("target_price"),
//            (String) negotiationParams.get("quantity_range"),
//            (String) negotiationParams.get("negotiation_strategy"),
//            (Integer) negotiationParams.get("timeout_seconds")
//        );
//
//        // Assuming the API expects some form of 'text' input for processing
//        partsNode.put("text", productDescription + ". " + negotiationSummary);
//        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
//
//        ObjectNode requestBodyNode = objectMapper.createObjectNode();
//        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));
//
//        // Convert the request body to a JSON string
//        String requestBody;
//        try {
//            requestBody = objectMapper.writeValueAsString(requestBodyNode);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to construct JSON request body", e);
//        }
//
//        // Create an HTTP entity with the request body and headers
//        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//
//        // Send the POST request to the Gemini API
//        Map<String, Object> negotiationResult;
//        try {
//            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);
//            negotiationResult = response.getBody();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to communicate with Gemini API", e);
//        }
//
//        // Analyze the response for success or failure
//        return analyzeNegotiationResult(negotiationResult, negotiationParams);
//    }
//
//    private Map<String, Object> analyzeNegotiationResult(Map<String, Object> negotiationResult, Map<String, Object> negotiationParams) {
//        Map<String, Object> response = new HashMap<>();
//
//        // Check if negotiation was successful and extract relevant information
//        if (negotiationResult != null && negotiationResult.containsKey("candidates")) {
//            // Extract chat analysis from the response
//            @SuppressWarnings("unchecked")
//            Map<String, Object> candidates = (Map<String, Object>) ((List<Map<String, Object>>) negotiationResult.get("candidates")).get(0);
//            Map<String, Object> content = (Map<String, Object>) candidates.get("content");
//            String negotiationAnalysis = (String) ((List<Map<String, Object>>) content.get("parts")).get(0).get("text");
//
//            // Determine success or failure
//            double targetPrice = (double) negotiationParams.get("target_price");
//            double originalPrice = (double) negotiationParams.get("price");
//            if (negotiationAnalysis.toLowerCase().contains("successful") && targetPrice < originalPrice) {
//                double allocatedPrice = targetPrice * 0.9; // 10% discount if target price is acceptable
//                response.put("success", true);
//                response.put("allocatedPrice", allocatedPrice);
//                response.put("chatAnalysis", negotiationAnalysis);
//            } else {
//                response.put("success", false);
//                response.put("error", "Negotiation failed: " + negotiationAnalysis);
//            }
//        } else {
//            response.put("success", false);
//            response.put("error", "Negotiation failed or no valid response received.");
//        }
//
//        return response;
//    }
//}
//

package com.praveen.AI.Bargaining.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

    private static final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s"; // Replace with actual URL

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> negotiate(Map<String, Object> product, Map<String, Object> negotiationParams) {
        // Validate inputs
        Double price = (Double) product.get("price");
        Double targetPrice = (Double) negotiationParams.get("target_price");
        if (price == null || targetPrice == null) {
            throw new IllegalArgumentException("Product price and target price must not be null.");
        }

        // Simulate negotiation logic
        Map<String, Object> response = new HashMap<>();

        // Example negotiation logic
        double finalPrice;

        if (targetPrice > 0 && targetPrice < price) {
            finalPrice = targetPrice * 0.9; // 10% discount if target is realistic
            response.put("success", true);
            response.put("allocatedPrice", finalPrice);
        } else {
            response.put("success", false);
            response.put("error", "Negotiation failed due to unrealistic target price.");
        }

        // Add any additional negotiation logic here...

        return response;
    }
}

