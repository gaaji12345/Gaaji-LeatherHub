package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    12/10/2024
    */

import com.example.GaajiShoe.dto.SupplierDTO;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.service.impl.SupplierServiceImpl;
import com.example.GaajiShoe.util.ResponceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {


    @Autowired
    private SupplierServiceImpl supplierService;

    @GetMapping()
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/code")
    public ResponseEntity<String> generateSupplierCode() {
        String supplierCode = supplierService.generateSupplierCode();
        return new ResponseEntity<>(supplierCode, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setSupplierCode(supplierService.generateSupplierCode());
        SupplierDTO savedSupplier = supplierService.saveSupplier(supplierDTO);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SupplierDTO> updateSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplier(supplierDTO);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }



    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponceUtil deletCustomer(@RequestParam("id") String id){
        supplierService.deleteSupplier(id);
        return new ResponceUtil(200,"Deleted",null);

    }






}
