//package com.praveen.AI.Bargaining.Controller;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.praveen.AI.Bargaining.model.GeminiService;
//import com.praveen.AI.Bargaining.model.ProductRequest;
//
//@RestController
//@RequestMapping("/api/")
//public class NegotiationApp {
//
//    @Autowired
//    private GeminiService geminiService;
//
//    @PostMapping("/negotiate")
//    public Map<String, Object> negotiateProduct(@RequestBody ProductRequest request) {
//        // Map the product details to the Gemini API format
//        Map<String, Object> product = new HashMap<>();
//        product.put("name", request.getName());
//        product.put("description", request.getDescription());
//        product.put("price", request.getPrice());
//        product.put("quantity", request.getQuantity());
//
//        // Map negotiation parameters
//        Map<String, Object> negotiationParams = new HashMap<>();
//        negotiationParams.put("target_price", request.getTargetPrice());
//        negotiationParams.put("quantity_range", request.getQuantityRange());
//        negotiationParams.put("negotiation_strategy", request.getNegotiationStrategy());
//        negotiationParams.put("timeout_seconds", request.getTimeoutSeconds());
//
//        // Call the Gemini API to negotiate
//        Map<String, Object> negotiationResult = geminiService.negotiate(product, negotiationParams);
//
//        // Check if negotiation was successful and return structured response
//        if (negotiationResult != null && negotiationResult.containsKey("candidates")) {
//            List<Map<String, Object>> candidatesList = (List<Map<String, Object>>) negotiationResult.get("candidates");
//
//            if (!candidatesList.isEmpty()) {
//                Map<String, Object> firstCandidate = candidatesList.get(0);
//                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
//                List<Map<String, Object>> partsList = (List<Map<String, Object>>) content.get("parts");
//
//                if (!partsList.isEmpty()) {
//                    Map<String, Object> firstPart = partsList.get(0);
//                    String negotiationAnalysis = (String) firstPart.get("text");
//
//                    // Create structured response
//                    Map<String, Object> response = new HashMap<>();
//                    response.put("product_name", request.getName());
//                    response.put("description", request.getDescription());
//                    response.put("requested_price", request.getPrice());
//                    response.put("target_price", request.getTargetPrice());
//                    response.put("quantity_requested", request.getQuantity());
//                    response.put("negotiation_analysis", negotiationAnalysis);
//
//                    return response;
//                }
//            }
//
//            return Map.of("error", "No valid response received");
//        } else {
//            return Map.of("error", "Negotiation failed or no valid response received");
//        }
//    }
//}
//

package com.praveen.AI.Bargaining.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praveen.AI.Bargaining.model.GeminiService;
import com.praveen.AI.Bargaining.model.ProductRequest;

@RestController
@RequestMapping("/api/")
public class NegotiationApp {

    
    private GeminiService geminiService;
    
 


	public NegotiationApp(GeminiService geminiService) {
		super();
		this.geminiService = geminiService;
	}


	@PostMapping("/negotiate")
    public Map<String, Object> negotiateProduct(@RequestBody ProductRequest request) {
        // Map the product details to the Gemini API format
        Map<String, Object> product = new HashMap<>();
        product.put("name", request.getName());
        product.put("description", request.getDescription());
        product.put("price", request.getPrice());
        product.put("quantity", request.getQuantity());

        // Map negotiation parameters
        Map<String, Object> negotiationParams = new HashMap<>();
        negotiationParams.put("target_price", request.getTargetPrice());
        negotiationParams.put("quantity_range", request.getQuantityRange());
        negotiationParams.put("negotiation_strategy", request.getNegotiationStrategy());
        negotiationParams.put("timeout_seconds", request.getTimeoutSeconds());

        // Call the Gemini API to negotiate
        Map<String, Object> negotiationResult = geminiService.negotiate(product, negotiationParams);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();

        if (negotiationResult != null) {
            boolean success = (boolean) negotiationResult.get("success");
            response.put("status", success ? "success" : "failure");
            response.put("allocatedPrice", negotiationResult.get("allocatedPrice"));
            response.put("analysis", success ? "Negotiation successful." : negotiationResult.get("error"));
        } else {
            response.put("status", "failure");
            response.put("allocatedPrice", null);
            response.put("error", "Negotiation failed or no valid response received.");
        }

        return response;
    }
}

