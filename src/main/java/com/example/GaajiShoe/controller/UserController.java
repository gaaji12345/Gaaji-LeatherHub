package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    10/09/2024
    */


import com.example.GaajiShoe.dto.Responce;
import com.example.GaajiShoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Responce> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

//    @GetMapping("/my-info")
//    public ResponseEntity<Responce> getUserInfoAndOrderHistory(){
//        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
//    }


}
