package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    14/10/2024
    */


import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.SlaesInventoryDTO;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Sales;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SalesDetailsRepo;
import com.example.GaajiShoe.repo.SalesRepo;
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
    SalesDetailsRepo salesDetailsRepo;
    @Autowired
    SalesRepo salesRepo;
    @Autowired
    ModelMapper mapper;

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
        if(inventoryRepo.existsByItemCode(inventoryDTO.getItemCode())){
            throw new NullPointerException("This Inventory "+inventoryDTO.getItemCode()+" already exicts...");
        }
        System.out.println(inventoryDTO.getItemCode());
        inventoryDTO.setItemCode(nextInventoryCode(inventoryDTO.getItemCode()));
        return mapper.map(inventoryRepo.save(mapper.map(
                inventoryDTO, Inventory.class)), InventoryDTO.class
        );
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

    @Override
    public String nextInventoryCode(String code) {
        String lastInventoryCode = "IIM"+" "+inventoryRepo.countInventoryRows();
        if(lastInventoryCode==null){lastInventoryCode = code+"000";}
        int numericPart = Integer.parseInt(lastInventoryCode.substring(3));
        numericPart++;
        String nextInventoryCode = code + String.format("%03d", numericPart);
        return nextInventoryCode;


    }



    @Override
    public List<InventoryDTO> getMostSaleItem() {
        LocalDate today = LocalDate.now();
        List<Sales> todaySales = salesRepo.findTodaySales(String.valueOf(today));
        List<SlaesInventoryDTO> todaySaleInventoryDetails = new ArrayList<>();

        // Aggregate sales details into a single list
        for (Sales sale : todaySales) {
            List<SlaesInventoryDTO> orderDetails = salesDetailsRepo
                    .findAllBySalesOrderNo(sale.getOrderNo()).stream()
                    .map(details -> mapper.map(details, SlaesInventoryDTO.class))
                    .toList();
            todaySaleInventoryDetails.addAll(orderDetails);
        }

        // Combine quantities for the same item
        Map<String, SlaesInventoryDTO> inventoryMap = new HashMap<>();
        for (SlaesInventoryDTO dto : todaySaleInventoryDetails) {
            inventoryMap.merge(dto.getInventory().getItemCode(), dto,
                    (existing, newDto) -> {
                        existing.setQuantity(existing.getQuantity() + newDto.getQuantity());
                        return existing;
                    });
        }

        // Sort and prepare final list
        List<SlaesInventoryDTO> sortedInventoryDetails = new ArrayList<>(inventoryMap.values());
        sortedInventoryDetails.sort(Comparator.comparingInt(SlaesInventoryDTO::getQuantity).reversed());

        // Create final inventory list
        return sortedInventoryDetails.stream()
                .map(SlaesInventoryDTO::getInventory)
                .toList();
    }


//    @Override
//    public List<InventoryDTO> getMostSaleItem() {
//
//        List<Sales>getAllTodaySales;
//        List<SlaesInventoryDTO>getTodaySaleInventoryDetails = new ArrayList<>();
//        List<SlaesInventoryDTO>TodaySaleInventoryDetails = new ArrayList<>();
//        Boolean notFound = false;
//        LocalDate today = LocalDate.now();
//        getAllTodaySales = salesRepo.findTodaySales(String.valueOf(today));
//
//        for(int i = 0; i<getAllTodaySales.size(); i++){
//            List<SlaesInventoryDTO>getOneOrderSalesDetails = salesDetailsRepo.findAllBySalesOrderNo(getAllTodaySales.get(i).getOrderNo()).stream().map(
//                    salesDetails -> mapper.map(salesDetails, SlaesInventoryDTO.class)
//            ).toList();
//            for(SlaesInventoryDTO salesInventoryDTO:getOneOrderSalesDetails){
//                getTodaySaleInventoryDetails.add(salesInventoryDTO);
//            }
//        }
//
//        System.out.println(getTodaySaleInventoryDetails.size());
//        for(int i = 0; i<getTodaySaleInventoryDetails.size(); i++){
//            if(TodaySaleInventoryDetails.size()>0) {
//                L:for (int j = 0; j < TodaySaleInventoryDetails.size(); j++) {
//                    if(getTodaySaleInventoryDetails.get(i).getInventory().getItemCode().equals(
//                            TodaySaleInventoryDetails.get(j).getInventory().getItemCode()
//                    )){
//                        System.out.println("comming!");
//                        TodaySaleInventoryDetails.get(j).setQuantity(
//                                TodaySaleInventoryDetails.get(j).getQuantity()+getTodaySaleInventoryDetails.get(i).getQuantity()
//                        );
//                        notFound = false;
//                        break L;
//                    }else {notFound = true;}
//                }
//                if(notFound){
//                    TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
//                }
//            }else{
//                TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
//            }
//        }
//        TodaySaleInventoryDetails = sortAsSaleItemsQuantity(TodaySaleInventoryDetails);
//        List<InventoryDTO>invetorys = new ArrayList<>();
//        for(int i = TodaySaleInventoryDetails.size()-1; i >=0; i--){
//            System.out.println(TodaySaleInventoryDetails.get(i).getInventory());
//            invetorys.add(TodaySaleInventoryDetails.get(i).getInventory());
//            System.out.println(TodaySaleInventoryDetails.get(i).getQuantity());
//        }
//
//        return invetorys;
//    }
//
//    private List<SlaesInventoryDTO> sortAsSaleItemsQuantity(List<SlaesInventoryDTO> list){
//        list.sort(Comparator.comparingInt(SlaesInventoryDTO::getQuantity));
//        return list;
//    }

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
}
