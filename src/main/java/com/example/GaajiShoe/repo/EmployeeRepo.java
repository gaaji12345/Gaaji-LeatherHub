package com.example.GaajiShoe.repo;/*  gaajiCode
    99
    08/10/2024
    */

import com.example.GaajiShoe.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,String> {

    Boolean existsByEmployeeCode(String id);
    Employee findByEmployeeCode(String id);

    @Query(value = "SELECT employee_code FROM Employee ORDER BY employee_code DESC LIMIT 1", nativeQuery = true)
    String findLatestEmployeeCode();
    @Query(value = "SELECT * FROM employee e " +
            "WHERE (MONTH(e.dob) > MONTH(CURDATE())) " +
            "OR (MONTH(e.dob) = MONTH(CURDATE()) AND DAY(e.dob) >= DAY(CURDATE())) " +
            "ORDER BY MONTH(e.dob), DAY(e.dob)",
            nativeQuery = true)
    List<Employee> findEmployeesWithBirthdaysTodayAndAfter();

}
