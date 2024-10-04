package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.Level;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_code;
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
    private Date recent_purchase_date_time;


}
