package com.example.GaajiShoe.service;/*  gaajiCode
    99
    02/10/2024
    */

import com.example.GaajiShoe.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
//     void saveCustomer(CustomerDTO c);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

     void updateCustomer(CustomerDTO c);

    void deleteCustomer(String id);

     CustomerDTO searchCustomer(String id);


    List<CustomerDTO> getAllCustomers();

    String genarateNextCustomerCode();
}
