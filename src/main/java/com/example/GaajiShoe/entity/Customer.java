package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    private String customerName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinDate;
    @Enumerated(EnumType.STRING)
    private Level level;
    private int totalPoints;
    private Date dob;
    private String address_line_01;
    private String address_line_02;
    private String address_line_03;
    private String address_line_04;
    private String address_line_05;

    private String contact_nmb;
    private String email;
    private Date recent_purchase_date_time;


}
