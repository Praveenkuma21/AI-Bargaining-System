package com.praveen.AI.Bargaining.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class GeminiService {

    private static final String API_URL_TEMPLATE = 
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s"; 

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

        // Prepare the prompt for the chatbot including the target price
        String prompt = String.format("Negotiate the price for the product '%s' priced at %.2f. The target price is %.2f.",
                product.get("name"), price, targetPrice);

        // Call the chatbot with the prompt
        String chatbotResponse = callChatbot(prompt);

        // Process the chatbot response to determine success and final price
        Map<String, Object> response = new HashMap<>();
        if (isSuccess(chatbotResponse)) {
            double finalPrice = targetPrice * 0.9; // Example: 10% discount if accepted
            response.put("success", true);
            response.put("allocatedPrice", finalPrice);
            response.put("chatbotResponse", "Negotiation successful. Final price allocated is: " + finalPrice);
        } else {
            response.put("success", false);
            response.put("error", "Negotiation failed: " + chatbotResponse);
            response.put("chatbotResponse", chatbotResponse);
        }

        return response;
    }

    private String callChatbot(String message) {
        String geminiKey = "AIzaSyAlsxmZwmQBZoC12txD1tFcncgoBelizAg"; // Your API key
        String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build JSON request body according to the provided schema
        ObjectNode requestBodyNode = objectMapper.createObjectNode();

        // Create the contents array
        ArrayNode contentsArray = objectMapper.createArrayNode();
        ObjectNode contentNode = objectMapper.createObjectNode();
        contentNode.put("role", "user");

        // Create the parts array
        ArrayNode partsArray = objectMapper.createArrayNode();
        ObjectNode partNode = objectMapper.createObjectNode();
        partNode.put("text", message); // The message you want to send

        // Add part to parts array
        partsArray.add(partNode);
        contentNode.set("parts", partsArray);
        contentsArray.add(contentNode);
        requestBodyNode.set("contents", contentsArray);

        // Add generation configuration
        ObjectNode generationConfigNode = objectMapper.createObjectNode();
        generationConfigNode.put("temperature", 0.7);
        generationConfigNode.put("maxOutputTokens", 150);
        requestBodyNode.set("generationConfig", generationConfigNode);

        // Create an HTTP entity with the request body and headers
        HttpEntity<String> request = new HttpEntity<>(requestBodyNode.toString(), headers);

        // Send the POST request to the Gemini API
        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);

            // Log the response status and body for debugging
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            // Check if the response is valid
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    
                    if (!parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
                return "No valid candidates returned from the chatbot API.";
            } else {
                return "Unexpected response from the chatbot API.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to communicate with the chatbot API: " + e.getMessage();
        }
    }


    private boolean isSuccess(String responseText) {
        // Define success and failure keywords
        List<String> successKeywords = Arrays.asList("accepted", "yes", "success", "ok", "positive");
        List<String> failureKeywords = Arrays.asList("not", "no", "failed", "declined", "rejected");

        // Check for success keywords
        for (String keyword : successKeywords) {
            if (responseText.toLowerCase().contains(keyword)) {
                return true;
            }
        }

        // Check for failure keywords
        for (String keyword : failureKeywords) {
            if (responseText.toLowerCase().contains(keyword)) {
                return false;
            }
        }

        // Default to failure if no clear indication
        return false;
    }
}
