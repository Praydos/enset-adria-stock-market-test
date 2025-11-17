package org.example.stockservice.web;


import lombok.AllArgsConstructor;
import org.example.stockservice.dtos.StockMarketDTO;
import org.example.stockservice.entities.StockMarket;
import org.example.stockservice.services.StockMarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@AllArgsConstructor
public class StockMarketController {

    private final StockMarketService stockMarketService;

    @PostMapping
    public ResponseEntity<StockMarket> addQuotation(@RequestBody StockMarketDTO stockMarketDTO) {
        StockMarket savedQuotation = stockMarketService.addQuotation(stockMarketDTO);
        return new ResponseEntity<>(savedQuotation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        stockMarketService.deleteQuotation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/current-price/{companyId}")
    public ResponseEntity<Double> getCurrentStockPrice(@PathVariable String companyId) {
        Double currentPrice = stockMarketService.getCurrentStockPrice(companyId);
        return new ResponseEntity<>(currentPrice, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StockMarket>> getAllQuotations() {
        List<StockMarket> quotations = stockMarketService.getAllQuotations();
        return new ResponseEntity<>(quotations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMarket> getQuotationById(@PathVariable Long id) {
        StockMarket quotation = stockMarketService.getQuotationById(id);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }
}