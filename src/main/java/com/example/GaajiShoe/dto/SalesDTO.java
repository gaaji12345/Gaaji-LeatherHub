package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    14/10/2024
    */


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesDTO {

    private List<SlaesInventoryDTO> inventory;
    private String orderNo;
    private String customerName;
    private Double totalPrice;
    private Date purchaseDate;
    private String paymentMethod;
    private Double addedPoints;
    private String cashierName;
}
