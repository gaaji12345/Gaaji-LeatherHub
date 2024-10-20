package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    20/10/2024
    */

import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.SalesDTO;
import com.example.GaajiShoe.dto.SlaesInventoryDTO;
import com.example.GaajiShoe.entity.Inventory;
import com.example.GaajiShoe.entity.Sales;
import com.example.GaajiShoe.entity.SalesDetails;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SalesDetailsRepo;
import com.example.GaajiShoe.repo.SalesRepo;
import com.example.GaajiShoe.service.SalesService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    @Autowired
    SalesRepo salesRepo;
    @Autowired
    SalesDetailsRepo detailsRepo;
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    ModelMapper mapper;


    @Override
    public List<SalesDTO> getAllSales() {
        List<Sales> salesList = salesRepo.findAll();
        return salesList.stream().map(sales -> {
            SalesDTO salesDTO = mapper.map(sales, SalesDTO.class);

            List<SalesDetails> salesDetailsList = detailsRepo.findAllBySalesOrderNo(sales.getOrderNo());
            List<SlaesInventoryDTO> salesInventoryDTOList = salesDetailsList.stream()
                    .map(details -> {
                        SlaesInventoryDTO salesInventoryDTO = mapper.map(details, SlaesInventoryDTO.class);
                        salesInventoryDTO.setInventory(mapper.map(details.getInventory(), InventoryDTO.class));
                        return salesInventoryDTO;
                    })
                    .collect(Collectors.toList());

            salesDTO.setInventory(salesInventoryDTOList);
            return salesDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public SalesDTO getSaleDetails(String id) {
        if(!salesRepo.existsByOrderNo(id)){
            throw new NotFoundException("Sales "+id+" Not Found!");
        }
        SalesDTO salesDTO = mapper.map(salesRepo.findByOrderNo(id), SalesDTO.class);
        System.out.println("ID-----------------------"+id);
        List<SlaesInventoryDTO> salesInventory = detailsRepo.findAllBySalesOrderNo(id).stream().map(
                salesDetails -> mapper.map(salesDetails, SlaesInventoryDTO.class)
        ).toList();
        salesDTO.setInventory(salesInventory);

        return salesDTO;
    }

    @Transactional
    @Override
    public SalesDTO saveSales(SalesDTO salesDTO) {
        if(maintainInventoryQuantity(salesDTO)){
            if(salesRepo.existsByOrderNo(salesDTO.getOrderNo())){
                throw new NotFoundException("This Sales "+salesDTO.getOrderNo()+" already exicts...");
            }
            SalesDTO newsalesDTO = mapper.map(salesRepo.save(mapper.map(
                    salesDTO, Sales.class)), SalesDTO.class
            );

            List<SlaesInventoryDTO> salesInventoryDTO = new ArrayList<>();
            for (SlaesInventoryDTO inventoryDTO : salesDTO.getInventory()) {
                SalesDetails savedSaleDetails = detailsRepo.save(mapper.map(inventoryDTO, SalesDetails.class));
                salesInventoryDTO.add(mapper.map(savedSaleDetails, SlaesInventoryDTO.class));
            }
            newsalesDTO.setInventory(salesInventoryDTO);
            return newsalesDTO;
        }else {
            return salesDTO;
        }
    }

    @Override
    public void updateSales(String id, SalesDTO salesDTO) {

    }

    @Override
    public void deleteSales(String id) {

    }

    @Override
    public String nextOrderCode() {
        return null;
    }

    @Override
    public Map<String, Double> getWeeklyProfit() {
        return null;
    }

    @Override
    public Double getMonthlyRevenue() {
        return null;
    }
    protected Boolean maintainInventoryQuantity(SalesDTO salesDTO){
        boolean valid = false;
        int quantity;
        for (int i = 0; i<salesDTO.getInventory().size();i++){
            SlaesInventoryDTO inventoryDTO = salesDTO.getInventory().get(i);
            String itemCode = inventoryDTO.getInventory().getItemCode();
            System.out.println(itemCode);

            InventoryDTO inventory = mapper.map(inventoryRepo.findByItemCode(itemCode),InventoryDTO.class);
            if(inventory.getQuantity()>0){
                if(inventory.getQuantity()-inventoryDTO.getQuantity()>=0){
                    quantity = inventory.getQuantity()-inventoryDTO.getQuantity();
                    inventory.setQuantity(quantity);
                    if(quantity>100){
                        inventory.setStatus("available");
                    }else if(quantity<=100){
                        inventory.setStatus("low");
                    }else if(quantity==0){
                        inventory.setStatus("not");
                    }
                    inventoryRepo.save(mapper.map(inventory, Inventory.class));
                    valid = true;
                }else{
                    valid = false;
                    throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
                }
            }else{
                valid = false;
                throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
            }
        }
        return valid;
    }



}
