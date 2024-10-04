package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Long customer_code;
    private String customerName;
    private Gender gender;
    private Date joinDate;
    private Level level;
    private int totalPoints;
    private Date dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;
    private Date recentPurchaseDateTime;



}
