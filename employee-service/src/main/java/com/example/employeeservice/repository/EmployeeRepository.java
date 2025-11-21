package com.example.employeeservice.repository;

import com.example.employeeservice.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByEmployeeRoleId(UUID roleId);
    List<Employee> findByEmployeeRole_Name(String roleName);
}