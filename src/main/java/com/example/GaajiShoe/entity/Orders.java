package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    21/10/2024
    */

import com.example.GaajiShoe.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity

public class Orders {
    @Id
    private String orderNo;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customerCode", referencedColumnName = "customer_code", nullable = false)
    private Customer customerCode;
    private double totalPrice;
    private Timestamp purchaseDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private double addPoints;
    private String cashierName;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<OrderDetails> saleDetails;
}
