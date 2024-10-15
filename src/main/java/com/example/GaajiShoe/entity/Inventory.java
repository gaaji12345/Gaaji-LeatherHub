package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    12/10/2024
    */

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_picture", columnDefinition = "LONGTEXT")
    private String itemPicture;

    @Column(name = "category")
    private String category;

    @Column(name = "size")
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "supplier_code" , referencedColumnName = "supplier_code")
    private Supplier supplier;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "unit_price_sale")
    private Double unitPriceSale;

    @Column(name = "unit_price_buy")
    private Double unitPriceBuy;

    @Column(name = "expected_profit")
    private Double expectedProfit;

    @Column(name = "profit_margin")
    private Double profitMargin;

    @Column(name = "status")
    private String status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "p_quantity")
    private Integer pQuantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =  "inventory")
    private List<SalesDetails> salesDetails = new ArrayList<>();

}
