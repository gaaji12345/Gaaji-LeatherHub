package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    09/09/2024
    */


import com.example.GaajiShoe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    // Custom method to find a user by email


    Boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
