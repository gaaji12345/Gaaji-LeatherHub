package com.example.GaajiShoe.service;/*  gaajiCode
    99
    12/10/2024
    */


import com.example.GaajiShoe.dto.SupplierDTO;
import com.example.GaajiShoe.entity.Supplier;
import com.example.GaajiShoe.util.exeption.NotFoundException;

import java.util.List;

public interface SupplierService {

    public String generateSupplierCode();

    public SupplierDTO saveSupplier(SupplierDTO supplierDTO);
    public List<SupplierDTO> getAllSuppliers();


    public void deleteSupplier(String id) ;
//

    public SupplierDTO updateSupplier(SupplierDTO supplierDTO);

//    public List<String> getAllSupplierCodes();

}
