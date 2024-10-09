package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    08/10/2024
    */


import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {

    private String employeeCode;
    private String employeeName;
    private String empPic;
    private Gender gender;
    private String status;
    private String designation;
    private UserRole role;
    private Date dob;
    private Date dateOfJoin;
    private String attachedBranch;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;

    @Pattern(regexp = "^\\+?[0-9()-]{1,11}$", message = "Invalid contact number format")
    private String contactNo;
    private String email;
    private String emergencyContact;
    private String emergencyContactPerson;

}
