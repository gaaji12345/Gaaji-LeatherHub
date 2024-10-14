package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    14/10/2024
    */

import com.example.GaajiShoe.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepo extends JpaRepository<Inventory,String> {
    Boolean existsByItemCode(String id);
    Inventory findByItemCode(String id);
    void deleteByItemCode(String id);
    @Query(value = "SELECT COUNT(*) FROM Inventory", nativeQuery = true)
    Long countInventoryRows();
}
