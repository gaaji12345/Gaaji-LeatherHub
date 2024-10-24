package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    21/10/2024
    */

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailsDTO {
    private String orderNo;
    private String itemCode;
    @Positive(message = "Unit price must be a positive value")
    private double unitPriceBuy;
    @Positive(message = "Unit price must be a positive value")
    private double unitPriceSale;
    @Positive(message = "Quantity must be a positive integer value")
    private int quantity;
}
