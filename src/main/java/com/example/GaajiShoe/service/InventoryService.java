package com.example.GaajiShoe.service;

/*  gaajiCode
    99
    14/10/2024
    */

import com.example.GaajiShoe.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getAllInventory();
    InventoryDTO getInventoryDetails(String id);
    InventoryDTO saveInventory(InventoryDTO inventoryDTO);
    void updateInventory(String id, InventoryDTO inventoryDTO);
    void deleteInventory(String id);
    String nextInventoryCode(String code);
    List<InventoryDTO> getMostSaleItem();

}
