package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.dto.SupplierDTO;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.repo.SupplierRepo;
import com.example.GaajiShoe.util.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl {

    @Autowired
    SupplierRepo supplierRepository;

    private AtomicInteger counter = new AtomicInteger(1);

   @Autowired
    SupplierMapper supplierMapper;

    public String generateSupplierCode() {
        return "SUP" + String.format("%03d", counter.getAndIncrement());
    }

    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        return supplierMapper.toDTO(supplierRepository.save(supplier));
    }

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteSupplier(String supplierCode) {
        supplierRepository.deleteById(supplierCode);
    }

    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        return supplierMapper.toDTO(supplierRepository.save(supplier));
    }


}
