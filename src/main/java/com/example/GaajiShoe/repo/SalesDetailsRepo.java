package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    14/10/2024
    */

import com.example.GaajiShoe.entity.SalesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesDetailsRepo extends JpaRepository<SalesDetails,String> {
    Boolean existsBySalesOrderNo(String id);
    List<SalesDetails> findAllBySalesOrderNo(String id);
    void deleteAllBySalesOrderNo(String id);
}
