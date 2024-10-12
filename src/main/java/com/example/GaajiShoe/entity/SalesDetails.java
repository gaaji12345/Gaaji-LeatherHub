package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    12/10/2024
    */

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SalesDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int salesId;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "size", nullable = true)
    private Integer size;

    @Column(name = "unit_price_sale")
    private Double unitPriceSale;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "item_code" , referencedColumnName = "item_code")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "order_no" , referencedColumnName = "order_no")
    private Sales sales;
}
