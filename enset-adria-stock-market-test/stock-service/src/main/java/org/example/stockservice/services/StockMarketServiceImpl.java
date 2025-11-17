package org.example.stockservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stockservice.dtos.StockMarketDTO;
import org.example.stockservice.entities.StockMarket;
import org.example.stockservice.repositories.StockMarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StockMarketServiceImpl implements StockMarketService {

    private final StockMarketRepository stockMarketRepository;

    @Override
    public StockMarket addQuotation(StockMarketDTO stockMarketDTO) {
        log.info("Adding new quotation for company: {}", stockMarketDTO.getCompanyId());

        StockMarket stockMarket = new StockMarket();
        stockMarket.setDate(stockMarketDTO.getDate());
        stockMarket.setOpenValue(stockMarketDTO.getOpenValue());
        stockMarket.setCloseValue(stockMarketDTO.getCloseValue());
        stockMarket.setVolume(stockMarketDTO.getVolume());
        stockMarket.setCompanyId(stockMarketDTO.getCompanyId());

        return stockMarketRepository.save(stockMarket);
    }

    @Override
    public void deleteQuotation(Long id) {
        log.info("Deleting quotation with id: {}", id);
        if (stockMarketRepository.existsById(id)) {
            stockMarketRepository.deleteById(id);
            log.info("Quotation with id {} deleted successfully", id);
        } else {
            log.warn("Quotation with id {} not found", id);
            throw new RuntimeException("Quotation not found with id: " + id);
        }
    }

    @Override
    public Double getCurrentStockPrice(String companyId) {
        log.info("Getting current stock price for company: {}", companyId);
        StockMarket latestQuotation = stockMarketRepository.findTopByCompanyIdOrderByDateDesc(companyId);

        if (latestQuotation != null) {
            return latestQuotation.getCloseValue();
        } else {
            log.warn("No quotations found for company: {}", companyId);
            throw new RuntimeException("No quotations found for company: " + companyId);
        }
    }

    @Override
    public List<StockMarket> getAllQuotations() {
        log.info("Retrieving all quotations");
        return stockMarketRepository.findAll();
    }

    @Override
    public StockMarket getQuotationById(Long id) {
        log.info("Retrieving quotation with id: {}", id);
        return stockMarketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation not found with id: " + id));
    }
}