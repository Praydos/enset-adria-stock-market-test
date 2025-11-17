package org.example.stockservice;

import org.example.stockservice.entities.StockMarket;
import org.example.stockservice.repositories.StockMarketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class StockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner comandLineRunner(StockMarketRepository  stockMarketRepository) {
        return (args) -> {
            stockMarketRepository.save(new StockMarket(null, new Date(),100.0,200.0,10.0,"aa"));
            stockMarketRepository.save(new StockMarket(null, new Date(),90.0,50.0,10.0,"ab"));
            stockMarketRepository.save(new StockMarket(null, new Date(),120.0,200.0,10.0,"ac"));

        };
    }

}
