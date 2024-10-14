package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.dto.SupplierDTO;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.repo.SupplierRepo;
import com.example.GaajiShoe.service.SupplierService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import com.example.GaajiShoe.util.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@Transactional
public class SupplierServiceImpl  implements SupplierService {

    @Autowired
    SupplierRepo supplierRepository;

    private AtomicInteger counter = new AtomicInteger(1);

   @Autowired
    SupplierMapper supplierMapper;


   @Override
    public String generateSupplierCode() {
        return "SUP" + String.format("%03d", counter.getAndIncrement());
    }

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        return supplierMapper.toDTO(supplierRepository.save(supplier));
    }
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSupplier(String id) {
//        supplierRepository.deleteById(supplierCode);
        if(!supplierRepository.existsBySupplierCode(id)){
            throw  new NotFoundException("Supplier ID"+ id + "Not Found...");
        }
        supplierRepository.deleteBySupplierCode(id);
    }

    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        return supplierMapper.toDTO(supplierRepository.save(supplier));
    }


}
