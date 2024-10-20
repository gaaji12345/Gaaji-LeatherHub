package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    14/10/2024
    */


import com.example.GaajiShoe.dto.EmployeeDTO;
import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.InventoryRq;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.service.InventoryService;
import com.example.GaajiShoe.util.FileUploadUtil;
import com.example.GaajiShoe.util.ResponceUtil;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    private final String UPLOAD_DIR = "/Users/gaaji/Downloads/testUpload/"; // Change as needed


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<InventoryDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<InventoryDTO> saveInventory(
            @RequestParam("file") MultipartFile itemPic,
            @RequestParam("itemDescription") String itemDescription,
            @RequestParam("category") String category,
            @RequestParam("size") Integer size,
            @RequestParam("supplierCode") String supplierCode,
            @RequestParam("supplierName") String supplierName,
            @RequestParam("unitPriceSale") Double unitPriceSale,
            @RequestParam("unitPriceBuy") Double unitPriceBuy,
            @RequestParam("expectedProfit") Double expectedProfit,
            @RequestParam("profitMargin") Double profitMargin,
            @RequestParam("status") String status,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("pQuantity") Integer pQuantity) {

//        // Ensure the upload directory exists
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file
        String filePath = Paths.get(UPLOAD_DIR, itemPic.getOriginalFilename()).toString();
        try {
            itemPic.transferTo(new File(filePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Handle file upload error
        }
//


        // Create InventoryDTO object
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setItemDescription(itemDescription);
        inventoryDTO.setItemPicture(filePath); // Save the file path
        inventoryDTO.setCategory(category);
        inventoryDTO.setSize(size);
        inventoryDTO.setSupplierCode(supplierCode);
        inventoryDTO.setSupplierName(supplierName);
        inventoryDTO.setUnitPriceSale(unitPriceSale);
        inventoryDTO.setUnitPriceBuy(unitPriceBuy);
        inventoryDTO.setExpectedProfit(expectedProfit);
        inventoryDTO.setProfitMargin(profitMargin);
        inventoryDTO.setStatus(status);
        inventoryDTO.setQuantity(quantity);
        inventoryDTO.setPQuantity(pQuantity);

        // Save inventory and convert back to DTO
        InventoryDTO savedInventory = inventoryService.saveInventory(inventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }




    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    InventoryDTO getInventory(@PathVariable("id") String id) {
        return inventoryService.getInventoryDetails(id);
    }


    @GetMapping("/mostsaleitem")
    List<InventoryDTO> getMostSaleInvetory() {
        return inventoryService.getMostSaleItem();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponceUtil deletCustomer(@RequestParam("inventoryCode") String inventoryCode){
        inventoryService.deleteInventory(inventoryCode);
        return new ResponceUtil(200,"Deleted",null);

    }

    @GetMapping("/generateInventoryCode")
    public String generateInventoryCode(@RequestParam("prefix") String prefix) {
        return inventoryService.nextInventoryCode(prefix);
    }



    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<InventoryDTO> updateInventory(
            @PathVariable("id") String id,
            @RequestParam(value = "file", required = false) MultipartFile itemPic,
            @ModelAttribute InventoryDTO inventoryDTO) {

        // Set the item code in the DTO
        inventoryDTO.setItemCode(id);

        // Handle the file if it's provided
        if (itemPic != null && !itemPic.isEmpty()) {
            try {
                // Convert the file to byte array and store it as needed
                byte[] fileBytes = itemPic.getBytes();
                // Example: Store as Base64
                inventoryDTO.setItemPicture(Base64.getEncoder().encodeToString(fileBytes));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null); // Handle file processing error
            }
        }

        try {
            // Call the service method to update the inventory
            inventoryService.updateInventory(id, inventoryDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return the updated inventory
        return ResponseEntity.ok(inventoryDTO);
    }


}
