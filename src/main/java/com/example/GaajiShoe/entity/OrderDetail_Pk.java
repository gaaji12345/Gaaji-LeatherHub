package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    21/10/2024
    */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail_Pk implements Serializable {
    private String orderNo;
    private String item_code;
}
