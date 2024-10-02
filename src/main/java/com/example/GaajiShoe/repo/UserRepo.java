package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    09/09/2024
    */


import com.example.GaajiShoe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
