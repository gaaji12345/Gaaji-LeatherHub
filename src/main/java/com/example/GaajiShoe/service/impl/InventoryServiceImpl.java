package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    14/10/2024
    */


import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.SlaesInventoryDTO;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Sales;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SalesDetailsRepo;
import com.example.GaajiShoe.repo.SalesRepo;
import com.example.GaajiShoe.repo.SupplierRepo;
import com.example.GaajiShoe.service.InventoryService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class InventoryServiceImpl  implements InventoryService {
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    SalesDetailsRepo salesDetailsRepo;
    @Autowired
    SalesRepo salesRepo;
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


    @Override
    public List<InventoryDTO> getMostSaleItem(){
        List<Sales>getAllTodaySales;
        List<SlaesInventoryDTO>getTodaySaleInventoryDetails = new ArrayList<>();
        List<SlaesInventoryDTO>TodaySaleInventoryDetails = new ArrayList<>();
        Boolean notFound = false;
        LocalDate today = LocalDate.now();
        getAllTodaySales = salesRepo.findTodaySales(String.valueOf(today));
        //System.out.println(getAllTodaySales.get(0).getOrderNo());
        for(int i = 0; i<getAllTodaySales.size(); i++){
            List<SlaesInventoryDTO>getOneOrderSalesDetails = salesDetailsRepo.findAllBySalesOrderNo(getAllTodaySales.get(i).getOrderNo()).stream().map(
                    salesDetails -> mapper.map(salesDetails, SlaesInventoryDTO.class)
            ).toList();
            for(SlaesInventoryDTO salesInventoryDTO:getOneOrderSalesDetails){
                getTodaySaleInventoryDetails.add(salesInventoryDTO);
            }
        }
        System.out.println("/////////////////");
        System.out.println(getTodaySaleInventoryDetails.size());
        for(int i = 0; i<getTodaySaleInventoryDetails.size(); i++){
            if(TodaySaleInventoryDetails.size()>0) {
                L:for (int j = 0; j < TodaySaleInventoryDetails.size(); j++) {
                    if(getTodaySaleInventoryDetails.get(i).getInventory().getItemCode().equals(
                            TodaySaleInventoryDetails.get(j).getInventory().getItemCode()
                    )){
                        System.out.println("comming!");
                        TodaySaleInventoryDetails.get(j).setQuantity(
                                TodaySaleInventoryDetails.get(j).getQuantity()+getTodaySaleInventoryDetails.get(i).getQuantity()
                        );
                        notFound = false;
                        break L;
                    }else {notFound = true;}
                }
                if(notFound){
                    TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
                }
            }else{
                TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
            }
        }
        TodaySaleInventoryDetails = sortAsSaleItemsQuantity(TodaySaleInventoryDetails);
        List<InventoryDTO>invetorys = new ArrayList<>();
        for(int i = TodaySaleInventoryDetails.size()-1; i >=0; i--){
            System.out.println(TodaySaleInventoryDetails.get(i).getInventory());
            invetorys.add(TodaySaleInventoryDetails.get(i).getInventory());
            System.out.println(TodaySaleInventoryDetails.get(i).getQuantity());
        }

        return invetorys;
    }

    private List<SlaesInventoryDTO> sortAsSaleItemsQuantity(List<SlaesInventoryDTO> list){
        list.sort(Comparator.comparingInt(SlaesInventoryDTO::getQuantity));
        return list;
    }


}
