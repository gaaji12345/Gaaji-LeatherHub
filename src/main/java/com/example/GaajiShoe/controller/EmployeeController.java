package com.example.GaajiShoe.controller;/*  gaajiCode
    99
    08/10/2024
    */

import com.example.GaajiShoe.dto.EmployeeDTO;
import com.example.GaajiShoe.enums.Gender;
import com.example.GaajiShoe.enums.UserRole;
import com.example.GaajiShoe.service.impl.EmployeeServiceIMPL;
import com.example.GaajiShoe.util.ResponceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    EmployeeServiceIMPL employeeService;


//    @PostMapping(consumes = "multipart/form-data")
//    public ResponseEntity<Employee> saveEmployee(
//            @RequestParam("employeeCode") String employeeCode,
//            @RequestParam("profilePic") MultipartFile profilePic) {
//        try {
//            Employee savedEmployee = employeeService.saveEmployee(employeeCode, profilePic);
//            return ResponseEntity.status(201).body(savedEmployee);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }

//    @PostMapping
//    public ResponseEntity<EmployeeDTO> createEmployee(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("employee") EmployeeDTO employeeDTO) throws IOException {
//
//        // Handle file upload, convert to base64 or store URL
//        employeeDTO.setEmpPic(Arrays.toString(file.getBytes())); // Convert to byte array or handle properly
//        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);
//        return ResponseEntity.ok(savedEmployee);
//    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeName") String employeeName,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status,
            @RequestParam("designation") String designation,
            @RequestParam("role") String role,
            @RequestParam("dob") String dobStr,  // Receive as String
            @RequestParam("dateOfJoin") String dateOfJoinStr, // Receive as String
            @RequestParam("attachedBranch") String attachedBranch,
            @RequestParam("addressLine01") String addressLine01,
            @RequestParam("addressLine02") String addressLine02,
            @RequestParam("addressLine03") String addressLine03,
            @RequestParam("addressLine04") String addressLine04,
            @RequestParam("addressLine05") String addressLine05,
            @RequestParam("contactNo") String contactNo,
            @RequestParam("email") String email,
            @RequestParam("emergencyContact") String emergencyContact,
            @RequestParam("emergencyContactPerson") String emergencyContactPerson) throws IOException, ParseException, ParseException {

        // Convert Strings to Dates
        Date dob = dateFormat.parse(dobStr);
        Date dateOfJoin = dateFormat.parse(dateOfJoinStr);

        // Handle file upload (save to directory)
        String uploadDir = "/Users/gaaji/Downloads/testUpload/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        // Convert file to Base64 (or store it directly as URL)
        String empPic = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(filePath)));

        // Create EmployeeDTO and set all fields
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName(employeeName);
        employeeDTO.setEmpPic(empPic);
        employeeDTO.setGender(Gender.valueOf(gender));
        employeeDTO.setStatus(status);
        employeeDTO.setDesignation(designation);
        employeeDTO.setRole(UserRole.valueOf(role));
        employeeDTO.setDob(dob);
        employeeDTO.setDateOfJoin(dateOfJoin);
        employeeDTO.setAttachedBranch(attachedBranch);
        employeeDTO.setAddressLine01(addressLine01);
        employeeDTO.setAddressLine02(addressLine02);
        employeeDTO.setAddressLine03(addressLine03);
        employeeDTO.setAddressLine04(addressLine04);
        employeeDTO.setAddressLine05(addressLine05);
        employeeDTO.setContactNo(contactNo);
        employeeDTO.setEmail(email);
        employeeDTO.setEmergencyContact(emergencyContact);
        employeeDTO.setEmergencyContactPerson(emergencyContactPerson);

        // Save the employee
        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }



    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String employeeCode) {
        return employeeService.getEmployeeByCode(employeeCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping()
    public ResponceUtil deleteEmployee(@RequestParam("employeeCode") String employeeCode) {
        employeeService.deleteEmployee(employeeCode);
        return new ResponceUtil(200,"Deleted",null);

    }


    @PutMapping("/{employeeCode}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable String employeeCode,
            @RequestParam("file") MultipartFile file,
            @RequestParam("employee") EmployeeDTO updatedEmployeeDTO) throws IOException {

        updatedEmployeeDTO.setEmpPic(Arrays.toString(file.getBytes())); // Handle file properly
        EmployeeDTO updatedEmp = employeeService.updateEmployee(employeeCode, updatedEmployeeDTO);
        return updatedEmp != null ? ResponseEntity.ok(updatedEmp) : ResponseEntity.notFound().build();
    }


}
