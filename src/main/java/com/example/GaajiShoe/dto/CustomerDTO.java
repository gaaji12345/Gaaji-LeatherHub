package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.Level;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    @Null(message = "Customer CODE is auto genarated")
    private String customerCode;
    private String customerName;
    private Gender gender;
    private Date joinDate;
    private Level level;
    private int totalPoints;
    private Date dob;
    private String address_line_01;
    private String address_line_02;
    private String address_line_03;
    private String address_line_04;
    private String address_line_05;
    private String contactNo;
    private String email;
    private Date recentPurchaseDateTime;



}
