package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    20/10/2024
    */

import com.example.GaajiShoe.dto.InventoryDTO;
import com.example.GaajiShoe.dto.SalesDTO;
import com.example.GaajiShoe.dto.SlaesInventoryDTO;
import com.example.GaajiShoe.entity.Sales;
import com.example.GaajiShoe.entity.SalesDetails;
import com.example.GaajiShoe.repo.InventoryRepo;
import com.example.GaajiShoe.repo.SalesDetailsRepo;
import com.example.GaajiShoe.repo.SalesRepo;
import com.example.GaajiShoe.service.SalesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
