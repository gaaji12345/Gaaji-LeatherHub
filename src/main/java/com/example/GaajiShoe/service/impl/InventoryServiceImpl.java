package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    14/10/2024
    */


import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SupplierRepo;
import com.example.GaajiShoe.service.InventoryService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class InventoryServiceImpl  implements InventoryService {
    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    ModelMapper mapper;

    @Autowired
    SupplierRepo repo;

    @Override
    public List<InventoryDTO> getAllInventory() {
        manageStatus();
        return inventoryRepo.findAll().stream().map(
                inventory -> mapper.map(inventory, InventoryDTO.class)
        ).toList();
    }

    @Override
    public InventoryDTO getInventoryDetails(String id) {
        if(!inventoryRepo.existsByItemCode(id)){
            throw new NotFoundException("Inventory "+id+" Not Found!");
        }
        return mapper.map(inventoryRepo.findByItemCode(id), InventoryDTO.class);
    }



    @Override
    public InventoryDTO saveInventory(InventoryDTO inventoryDTO) {
        // Automatically generate the item code if not provided
        if (inventoryDTO.getItemCode() == null || inventoryDTO.getItemCode().isEmpty()) {
            inventoryDTO.setItemCode(nextInventoryCode("IIM")); // Generate a new code with the prefix "IIM"
        } else {
            // Check if the provided code already exists
            if (inventoryRepo.existsByItemCode(inventoryDTO.getItemCode())) {
                throw new IllegalArgumentException("This Inventory " + inventoryDTO.getItemCode() + " already exists...");
            }
        }

        // Fetch Supplier entity by supplierCode
        Supplier supplier = repo.findBySupplierCode(inventoryDTO.getSupplierCode());
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier not found with code: " + inventoryDTO.getSupplierCode());
        }

        // Map DTO to Entity
        Inventory inventory = mapper.map(inventoryDTO, Inventory.class);
        inventory.setSupplier(supplier); // Set the Supplier entity

        // Save the entity
        Inventory savedInventory = inventoryRepo.save(inventory);

        // Convert back to DTO using ModelMapper
        return mapper.map(savedInventory, InventoryDTO.class);
    }

    @Override
    public void updateInventory(String id, InventoryDTO inventoryDTO) {

        Inventory existingInventory = inventoryRepo.findByItemCode(id);

        if(existingInventory.getItemCode().isEmpty()){
            throw new NotFoundException("Inventory "+ id + "Not Found...");
        }
        inventoryDTO.setItemCode(id);
        inventoryRepo.save(mapper.map(inventoryDTO,Inventory.class));
    }

    @Override
    public void deleteInventory(String id) {
        if(!inventoryRepo.existsByItemCode(id)){
            throw  new NotFoundException("Inventory "+ id + "Not Found...");
        }
        inventoryRepo.deleteByItemCode(id);

    }





    private void manageStatus(){
        InventoryDTO inventoryDTO;
        List<InventoryDTO>inventorys =  inventoryRepo.findAll().stream().map(
                inventory -> mapper.map(inventory, InventoryDTO.class)
        ).toList();

        for(int i = 0; i < inventorys.size(); i++){
            inventoryDTO = inventorys.get(i);
            if(inventorys.get(i).getQuantity()>=100){
                inventoryDTO.setStatus("Available");
            }else if(inventorys.get(i).getQuantity()<100 & inventorys.get(i).getQuantity()>0){
                inventoryDTO.setStatus("Low");
            }else if(inventorys.get(i).getQuantity()==0){
                inventoryDTO.setStatus("Not");
            }
            mapper.map(inventoryRepo.save(mapper.map(
                    inventoryDTO, Inventory.class)), InventoryDTO.class
            );
        }
    }

    @Override
    public String nextInventoryCode(String prefix) {
        // Get the count of inventory items
        long count = inventoryRepo.count(); // This gets the total number of rows

        // Generate the next inventory code
        String nextInventoryCode = prefix + String.format("%03d", count + 1);
        return nextInventoryCode;
    }





}
