package com.example.GaajiShoe.dto;
/*  gaajiCode
    99
    09/09/2024
    */

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
