package org.example.stockservice.services;

import org.example.stockservice.dtos.StockMarketDTO;
import org.example.stockservice.entities.StockMarket;

import java.util.List;

public interface StockMarketService {
    StockMarket addQuotation(StockMarketDTO stockMarketDTO);
    void deleteQuotation(Long id);
    Double getCurrentStockPrice(String companyId);
    List<StockMarket> getAllQuotations();
    StockMarket getQuotationById(Long id);
}