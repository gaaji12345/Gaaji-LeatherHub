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







//    public Employee saveEmployee(String employeeCode, MultipartFile profilePic) throws IOException {
//        // Ensure the upload directory exists
//        File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // Save the file
//        String filePath = uploadDir + profilePic.getOriginalFilename();
//        profilePic.transferTo(new File(filePath));
//
//        // Create Employee entity
//        Employee employee = new Employee();
//        employee.setEmployeeCode(employeeCode);
//        employee.setEmpPic(filePath); // Save the path in the database
//
//        return repo.save(employee);
//    }

//    @Override
//    public List<EmployeeDTO> getAllEmployees() {
//        return repo.findAll().stream().map(
//                employee -> modelMapper.map(employee, EmployeeDTO.class)
//        ).toList();
//    }

//    @Override
//    public EmployeeDTO getEmployeeDetails(String id) {
//        if(!repo.existsByEmployeeCode(id)){
//            throw new NotFoundException("Employee "+id+" Not Found!");
//        }
//        return modelMapper.map(repo.findByEmployeeCode(id), EmployeeDTO.class);
//    }



//    @Override
//    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
//        return null;
//    }
//
//    @Override
//    public void updateEmployee(String id, EmployeeDTO employeeDTO) {
//
//    }
//
//    @Override
//    public void deleteEmployee(String id) {
//
//    }
//
//    @Override
//    public String nextEmployeeCode() {
//
//        String lastEmployeeCode = repo.findLatestEmployeeCode();
//        if(lastEmployeeCode==null){lastEmployeeCode = "EM000";}
//        int numericPart = Integer.parseInt(lastEmployeeCode.substring(3));
//        numericPart++;
//        String nextEmployeeCode = "EM" + String.format("%03d", numericPart);
//        return nextEmployeeCode;
//    }
//
//    @Override
//    public List<EmployeeDTO> findAllEmployeesOrderByDob() {
//        return null;
//    }
}
