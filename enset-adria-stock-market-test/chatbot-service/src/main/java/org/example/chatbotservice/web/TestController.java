package org.example.chatbotservice.web;



import lombok.RequiredArgsConstructor;
import org.example.chatbotservice.clients.StockServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final StockServiceClient stockServiceClient;

    @GetMapping("/stock-connection")
    public String testStockConnection() {
        try {
            List<Map<String, Object>> quotations = stockServiceClient.getAllQuotations();
            return "✅ Successfully connected to Stock Service! Found " + quotations.size() + " quotations.";
        } catch (Exception e) {
            return "❌ Failed to connect to Stock Service: " + e.getMessage();
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Chatbot Service is running on port 8082";
    }
}