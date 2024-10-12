package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Supplier {
    @Id
    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "supplier_name")
    private String supplierName;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "address_line_01")
    private String addressLine01;

    @Column(name = "address_line_02")
    private String addressLine02;

    @Column(name = "address_line_03")
    private String addressLine03;

    @Column(name = "address_line_04")
    private String addressLine04;

    @Column(name = "address_line_05")
    private String addressLine05;

    @Column(name = "address_line_06")
    private String addressLine06;

    @Column(name = "contact_no1")
    private String contactNo1;

    @Column(name = "land_line_no")
    private String landLineNo;

    @Column(name = "email")
    private String email;



}
