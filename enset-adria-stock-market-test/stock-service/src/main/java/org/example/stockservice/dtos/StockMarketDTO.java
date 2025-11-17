package org.example.stockservice.dtos;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMarketDTO {
    private Long id;
    private Date date;
    private Double openValue;
    private Double closeValue;
    private Double volume;
    private String companyId;
}