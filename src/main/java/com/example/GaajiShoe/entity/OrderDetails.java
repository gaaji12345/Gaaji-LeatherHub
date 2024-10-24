package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    21/10/2024
    */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@IdClass(OrderDetail_Pk.class)
public class OrderDetails {
    @Id
    private String orderNo;

    @Id
    private String item_code;
    private double unitPriceBuy;
    private double unitPriceSale;
    private int quantity;
    //Out-Verse
    @ManyToOne
    @JoinColumn(name = "orderNo",referencedColumnName = "orderNo",insertable = false,updatable = false)
    private Orders sale;

    //Out-verse
    @ManyToOne
    @JoinColumn(name = "item_code",referencedColumnName = "item_code",insertable = false,updatable = false)
    private Inventory inventory;
}
