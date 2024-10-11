package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    10/09/2024
    */


import com.example.GaajiShoe.dto.LoginRequest;
import com.example.GaajiShoe.dto.Responce;
import com.example.GaajiShoe.dto.UserDTO;
import com.example.GaajiShoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

//    @DeleteMapping("/{email}")
//    public ResponseEntity<Responce> deleteUser(@PathVariable("email") String email) {
//        Responce response = userService.deleteUser(email);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    void deleteCustomer(@PathVariable("id") Long id){
//        userService.deleteUser(id);
//    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Responce> deleteUser(@PathVariable("email") Long email) {
        Responce response = userService.deleteUser(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Responce> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
