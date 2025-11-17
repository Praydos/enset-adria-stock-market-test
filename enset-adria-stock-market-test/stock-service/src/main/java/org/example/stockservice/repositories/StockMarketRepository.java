package org.example.stockservice.repositories;

import org.example.stockservice.entities.StockMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StockMarketRepository extends JpaRepository<StockMarket,Long> {
    // Find the latest stock price for a company
    StockMarket findTopByCompanyIdOrderByDateDesc(String companyId);
}
