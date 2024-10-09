package com.example.GaajiShoe.util.mapper;/*  gaajiCode
    99
    09/10/2024
    */

import com.example.GaajiShoe.dto.EmployeeDTO;
import com.example.GaajiShoe.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMaper {
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }
}
