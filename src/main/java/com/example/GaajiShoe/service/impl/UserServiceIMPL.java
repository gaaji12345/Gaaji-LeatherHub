package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    09/09/2024
    */


import com.example.GaajiShoe.config.JwtUtils;
import com.example.GaajiShoe.dto.LoginRequest;
import com.example.GaajiShoe.dto.Responce;
import com.example.GaajiShoe.dto.UserDTO;
import com.example.GaajiShoe.entity.User;
import com.example.GaajiShoe.enums.UserRole;
import com.example.GaajiShoe.repo.UserRepo;
import com.example.GaajiShoe.service.UserService;
import com.example.GaajiShoe.util.exeption.InvalidCredentialsException;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import com.example.GaajiShoe.util.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Responce registerUser(UserDTO registrationRequest) {
        UserRole role = UserRole.USER;

        if (registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser = userRepo.save(user);
        System.out.println(savedUser);

        UserDTO userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Responce.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDto)
                .build();
    }

    @Override
    public Responce loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);

        return Responce.builder()
                .status(200)
                .message("User Successfully Logged In")
                .token(token)
                .expirationTime("6 Month")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Responce getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> userDtos = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();

        return Responce.builder()
                .status(200)
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        log.info("User Email is: " + email);
        return userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));
    }



    @Override
    public Responce deleteUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + email));

        userRepo.delete(user);

        return Responce.builder()
                .status(200)
                .message("User successfully deleted")
                .build();
    }



//    @Override
//    public void deleteUser(String email) {
//        if(!userRepo.existsByEmail(email)){
//            throw  new NotFoundException("User email :"+ email + "Not Found...");
//        }
//        userRepo.deleteByEmail(email);
//    }


}
