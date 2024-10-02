package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    10/09/2024
    */


import com.example.GaajiShoe.dto.LoginRequest;
import com.example.GaajiShoe.dto.Responce;
import com.example.GaajiShoe.dto.UserDTO;
import com.example.GaajiShoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Responce> registerUser(@RequestBody UserDTO registrationRequest){
        System.out.println(registrationRequest);
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Responce> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
