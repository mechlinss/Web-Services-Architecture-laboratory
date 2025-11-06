package com.example.AUI_Labs.repository;

import com.example.AUI_Labs.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {
    List<EmployeeRole> findBySalary(Float salary);
    Optional<EmployeeRole> findByName(String name);
}