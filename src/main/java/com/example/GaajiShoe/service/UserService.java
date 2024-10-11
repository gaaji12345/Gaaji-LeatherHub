package com.example.GaajiShoe.service;/*  gaajiCode
    99
    09/09/2024
    */


import com.example.GaajiShoe.dto.LoginRequest;
import com.example.GaajiShoe.dto.Responce;
import com.example.GaajiShoe.dto.UserDTO;
import com.example.GaajiShoe.entity.User;

public interface UserService {
    Responce registerUser(UserDTO registrationRequest);
    Responce loginUser(LoginRequest loginRequest);
    Responce getAllUsers();
    User getLoginUser();
    public Responce deleteUser(Long id);

//    public void deleteUser(String email);

}
