package com.example.AUI_Labs.repository;

import com.example.AUI_Labs.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByEmployeeRole_Id(UUID roleId);
    List<Employee> findByEmployeeRole_Name(String roleName);
}