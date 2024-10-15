package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    14/10/2024
    */


import jakarta.validation.constraints.Null;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlaesInventoryDTO {
    @Null
    private String id;
    private InventoryDTO inventory;
    private String itemDescription;
    private Integer size;
    private Double unitPriceSale;
    private Integer quantity;
    private SalesDTO sales;
}
