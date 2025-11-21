package com.example.roleservice.repository;

import com.example.roleservice.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {
    Optional<EmployeeRole> findByName(String name);
}