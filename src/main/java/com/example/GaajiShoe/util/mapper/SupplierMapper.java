package com.example.GaajiShoe.util.mapper;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.dto.SupplierDTO;
import com.example.GaajiShoe.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierDTO toDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierCode(supplier.getSupplierCode());
        dto.setSupplierName(supplier.getSupplierName());
        dto.setCategory(supplier.getCategory());
        dto.setAddressLine01(supplier.getAddressLine01());
        dto.setAddressLine02(supplier.getAddressLine02());
        dto.setAddressLine03(supplier.getAddressLine03());
        dto.setAddressLine04(supplier.getAddressLine04());
        dto.setAddressLine05(supplier.getAddressLine05());
        dto.setAddressLine06(supplier.getAddressLine06());
        dto.setContactNo1(supplier.getContactNo1());
        dto.setLandLineNo(supplier.getLandLineNo());
        dto.setEmail(supplier.getEmail());
        return dto;
    }

    // Convert DTO to Entity
    public Supplier toEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setSupplierCode(dto.getSupplierCode());
        supplier.setSupplierName(dto.getSupplierName());
        supplier.setCategory(dto.getCategory());
        supplier.setAddressLine01(dto.getAddressLine01());
        supplier.setAddressLine02(dto.getAddressLine02());
        supplier.setAddressLine03(dto.getAddressLine03());
        supplier.setAddressLine04(dto.getAddressLine04());
        supplier.setAddressLine05(dto.getAddressLine05());
        supplier.setAddressLine06(dto.getAddressLine06());
        supplier.setContactNo1(dto.getContactNo1());
        supplier.setLandLineNo(dto.getLandLineNo());
        supplier.setEmail(dto.getEmail());
        return supplier;
    }
}

