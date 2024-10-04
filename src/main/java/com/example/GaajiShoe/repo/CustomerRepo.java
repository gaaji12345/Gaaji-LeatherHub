package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepo extends JpaRepository<Customer,String> {


    Customer findByCustomerCode(String id);
    Boolean existsByCustomerCode(String id);
    void deleteByCustomerCode(String id);
    @Query(value = "SELECT customer_code FROM Customers ORDER BY customer_code DESC LIMIT 1", nativeQuery = true)
    String findLatestCustomerCode();

}
