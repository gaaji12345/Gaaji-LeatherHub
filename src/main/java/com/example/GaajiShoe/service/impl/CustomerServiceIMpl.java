package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.dto.CustomerDTO;
import com.example.GaajiShoe.entity.Customer;
import com.example.GaajiShoe.repo.CustomerRepo;
import com.example.GaajiShoe.service.CustomerService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceIMpl implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ModelMapper modelMapper;



    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if(customerRepo.existsByCustomerCode(customerDTO.getCustomerCode())){
            throw new NotFoundException("This Customer "+customerDTO.getCustomerCode()+" already exicts...");
        }
        customerDTO.setCustomerCode(genarateNextCustomerCode());
        return modelMapper.map(customerRepo.save(modelMapper.map(
                customerDTO, Customer.class)), CustomerDTO.class
        );
    }

    @Override
    public void updateCustomer(CustomerDTO c) {

        Customer map = modelMapper.map(c, Customer.class);
        customerRepo.save(map);

    }

    @Override
    public void deleteCustomer(String id) {

        if(!customerRepo.existsByCustomerCode(id)){
            throw  new NotFoundException("Customer ID"+ id + "Not Found...");
        }
        customerRepo.deleteByCustomerCode(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) {

        if(!customerRepo.existsByCustomerCode(id)){
            throw new NotFoundException("Customer "+id+" Not Found!");
        }
        return modelMapper.map(customerRepo.findByCustomerCode(id), CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {


        List<Customer> all=customerRepo.findAll();

        return modelMapper.map(all,new TypeToken<List<CustomerDTO>>(){}.getType());
    }

    @Override
    public String genarateNextCustomerCode() {
        String lastCustomerCode = customerRepo.findLatestCustomerCode();
        if(lastCustomerCode==null){lastCustomerCode = "CUS000";}
        int numericPart = Integer.parseInt(lastCustomerCode.substring(3));
        numericPart++;
        String nextSupplierCode = "CUS" + String.format("%03d", numericPart);
        return nextSupplierCode;
    }
}
