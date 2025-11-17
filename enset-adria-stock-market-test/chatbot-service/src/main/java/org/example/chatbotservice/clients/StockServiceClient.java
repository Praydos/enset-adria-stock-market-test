package org.example.chatbotservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "stock-service", path = "/api/stock")
public interface StockServiceClient {

    @GetMapping
    List<Map<String, Object>> getAllQuotations();

    @GetMapping("/{id}")
    Map<String, Object> getQuotationById(@PathVariable Long id);

    @GetMapping("/current-price/{companyId}")
    Double getCurrentStockPrice(@PathVariable String companyId);

    @PostMapping
    Map<String, Object> addQuotation(@RequestBody Map<String, Object> quotationRequest);

    @DeleteMapping("/{id}")
    void deleteQuotation(@PathVariable Long id);
}