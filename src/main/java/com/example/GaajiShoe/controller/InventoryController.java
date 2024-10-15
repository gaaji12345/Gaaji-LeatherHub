package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    14/10/2024
    */


import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.service.InventoryService;
import com.example.GaajiShoe.util.ResponceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("inventory")
@CrossOrigin
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<InventoryDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }


//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    InventoryDTO saveInventory(@RequestPart("data") InventoryDTO inventoryDTO, @RequestPart("itempic") MultipartFile itempic){
//        String base64ProfilePic = null;
//        try {
//            base64ProfilePic = Base64.getEncoder().encodeToString(itempic.getBytes());
//            inventoryDTO.setItemPicture(
//                    base64ProfilePic
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return inventoryService.saveInventory(inventoryDTO);
//    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InventoryDTO> saveInventory(
            @RequestParam("file") MultipartFile itemPic,
//            @RequestParam("itemCode") String itemCode,
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
            @RequestParam("pQuantity") Integer pQuantity) throws IOException {

        // Handle file upload (save to directory)
        String uploadDir = "/Users/gaaji/Downloads/testUpload/"; // Change username
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + itemPic.getOriginalFilename();
        itemPic.transferTo(new File(filePath));

        // Convert file to Base64
        String base64ItemPic = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(filePath)));

        // Create InventoryDTO and set all fields
        InventoryDTO inventoryDTO = new InventoryDTO();
//        inventoryDTO.setItemCode(itemCode);
        inventoryDTO.setItemDescription(itemDescription);
        inventoryDTO.setItemPicture(base64ItemPic);
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

        // Save the inventory
        InventoryDTO savedInventory = inventoryService.saveInventory(inventoryDTO);
        return ResponseEntity.ok(savedInventory);
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
}


