package org.example.chatbotservice.mcp.tools;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatbotservice.clients.StockServiceClient;
import org.springframework.ai.tool.annotation.Tool; // Correct import
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockMarketTools {

    private final StockServiceClient stockServiceClient;

    @Tool(description = "Retrieve all stock market quotations")
    public List<Map<String, Object>> getAllStockQuotations() {
        log.info("MCP Tool: Getting all stock quotations");
        return stockServiceClient.getAllQuotations();
    }

    @Tool(description = "Get a specific stock quotation by its ID")
    public Map<String, Object> getStockQuotationById(Long id) {
        log.info("MCP Tool: Getting stock quotation by ID: {}", id);
        return stockServiceClient.getQuotationById(id);
    }

    @Tool(description = "Get the current stock price for a company based on the latest closing value")
    public Double getCurrentStockPrice(String companyId) {
        log.info("MCP Tool: Getting current stock price for company: {}", companyId);
        return stockServiceClient.getCurrentStockPrice(companyId);
    }

    @Tool(description = "Add a new stock market quotation")
    public Map<String, Object> addStockQuotation(
            String date,
            Double openValue,
            Double closeValue,
            Double volume,
            String companyId) {

        log.info("MCP Tool: Adding stock quotation for company: {}", companyId);

        Map<String, Object> quotationRequest = Map.of(
                "date", date,
                "openValue", openValue,
                "closeValue", closeValue,
                "volume", volume,
                "companyId", companyId
        );

        return stockServiceClient.addQuotation(quotationRequest);
    }

    @Tool(description = "Delete a stock quotation by its ID")
    public String deleteStockQuotation(Long id) {
        log.info("MCP Tool: Deleting stock quotation with ID: {}", id);
        stockServiceClient.deleteQuotation(id);
        return "Stock quotation with ID " + id + " deleted successfully";
    }
}