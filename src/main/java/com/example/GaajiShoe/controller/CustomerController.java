package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    04/10/2024
    */

import com.example.GaajiShoe.dto.CustomerDTO;
import com.example.GaajiShoe.service.CustomerService;
import com.example.GaajiShoe.util.ResponceUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@CrossOrigin
public class CustomerController {


    @Autowired
    CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponceUtil getAlCsutomer(){

        return new ResponceUtil(200,"OK",customerService.getAllCustomers());
    }


    @PostMapping(consumes = {})
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Void> saveCustomer(@Valid @RequestBody CustomerDTO dto) {
//        // System.out.println("Received customer data: " + dto.toString());
//        customerService.saveCustomer(dto);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping
//    public ResponceUtil saveCustomer(@RequestBody CustomerDTO c){
////        customerService.saveCustomer(c);
////        return new ResponceUtil(200,"OK",null);
//        customerService.saveCustomer(c);
//        return new ResponceUtil(200, "successfully added", c);
//    }

    @PutMapping()
    public  ResponceUtil  updateCustomer(@RequestBody CustomerDTO c){
        customerService.updateCustomer(c);
        return new ResponceUtil(200,"Updated",null);

    }

    @DeleteMapping
      @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponceUtil deletCustomer(@RequestParam("customerCode") String customerCode){
        customerService.deleteCustomer(customerCode);
        return new ResponceUtil(200,"Deleted",null);

    }

    @GetMapping(path = "/{id}")
    public ResponceUtil  searchCustomer(@PathVariable("id") String id){

        return new ResponceUtil(200,"OK", customerService.searchCustomer(id));


    }

    @GetMapping("/nextId")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextEmployeeCode(){
        return customerService.genarateNextCustomerCode();
    }
}
