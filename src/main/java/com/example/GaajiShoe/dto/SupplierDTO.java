package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO {
    private String supplierCode;
    private String supplierName;
    private Category category;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String addressLine06;
    private String contactNo1;
    private String landLineNo;
    private String email;

}
