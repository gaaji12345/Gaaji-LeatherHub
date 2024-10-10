package com.example.GaajiShoe.service.impl;/*  gaajiCode
    99
    08/10/2024
    */

import com.example.GaajiShoe.dto.EmployeeDTO;
import com.example.GaajiShoe.entity.Employee;
import com.example.GaajiShoe.repo.EmployeeRepo;
import com.example.GaajiShoe.service.EmployeeService;
import com.example.GaajiShoe.util.exeption.NotFoundException;
import com.example.GaajiShoe.util.mapper.EmployeeMaper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceIMPL  {


    private final String uploadDir = "/Users/gaaji/Downloads/testUpload/"; // Directory to save images

    @Autowired
    EmployeeRepo repo;


    @Autowired
    private EmployeeMaper employeeMapper;



    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setEmployeeCode(generateEmployeeCode());
        return employeeMapper.toDto(repo.save(employee));
    }


    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public Optional<EmployeeDTO> getEmployeeByCode(String employeeCode) {
        return repo.findById(employeeCode)
                .map(employeeMapper::toDto);
    }

    public void deleteEmployee(String employeeCode) {
       repo.deleteById(employeeCode);
    }

    public EmployeeDTO updateEmployee(String employeeCode, EmployeeDTO updatedEmployeeDTO) {
        return repo.findById(employeeCode)
                .map(existingEmployee -> {
                    Employee updatedEmployee = employeeMapper.toEntity(updatedEmployeeDTO);
                    updatedEmployee.setEmployeeCode(employeeCode); // Preserve employeeCode
                    return employeeMapper.toDto(repo.save(updatedEmployee));
                })
                .orElse(null);
    }


    private String generateEmployeeCode() {
        long count = repo.count();
        return String.format("EM%03d", count + 1);
    }








}
