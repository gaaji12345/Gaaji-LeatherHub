package com.example.GaajiShoe.dto;/*  gaajiCode
    99
    15/10/2024
    */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRq {
    private InventoryDTO inventoryDTO;
    private MultipartFile itempic;
}
