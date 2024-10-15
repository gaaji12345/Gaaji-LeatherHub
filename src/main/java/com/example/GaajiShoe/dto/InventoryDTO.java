package com.example.GaajiShoe.dto;
/*  gaajiCode
    99
    14/10/2024
    */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String itemCode;
    private String itemDescription;
    private String itemPicture;
    private String category;
    private Integer size;
    private String supplierCode;
    private String supplierName;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedProfit;
    private Double profitMargin;
    private String status;
    private Integer quantity;
    private Integer pQuantity;

}
