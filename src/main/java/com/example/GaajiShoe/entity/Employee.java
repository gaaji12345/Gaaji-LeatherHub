package com.example.GaajiShoe.entity;/*  gaajiCode
    99
    08/10/2024
    */

import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee {

    @Id
    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    private String employeeName;

    @Column(name = "employee_profile_pic" , columnDefinition = "LONGTEXT")
    private String empPic;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String status;
    private String designation;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Date dob;
    private Date dateOfJoin;
    @Column(name = "attached_branch")
    private  String attachedBranch;
    @Column(name = "address_line_01", nullable = false)
    private String addressLine01;

    @Column(name = "address_line_02", nullable = false)
    private String addressLine02;

    @Column(name = "address_line_03")
    private String addressLine03;

    @Column(name = "address_line_04")
    private String addressLine04;

    @Column(name = "address_line_05")
    private String addressLine05;

    @Column(name = "contact_no", nullable = false)
    private String contactNo;

    private String email;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_contact_person")
    private String emergencyContactPerson;




}
