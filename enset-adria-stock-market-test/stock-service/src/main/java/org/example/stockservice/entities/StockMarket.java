package org.example.stockservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StockMarket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double openValue;
    private Double closeValue;
    private Double volume;
    private String companyId;

}
