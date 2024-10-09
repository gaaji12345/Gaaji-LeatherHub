package com.example.GaajiShoe.service;/*  gaajiCode
    99
    08/10/2024
    */

import com.example.GaajiShoe.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeDetails(String id);
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    void updateEmployee(String id, EmployeeDTO employeeDTO);
    void deleteEmployee(String id);
    String nextEmployeeCode();
    List<EmployeeDTO> findAllEmployeesOrderByDob();
}
