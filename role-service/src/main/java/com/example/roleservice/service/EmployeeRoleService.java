package com.example.roleservice.service;

import com.example.roleservice.entity.EmployeeRole;
import com.example.roleservice.repository.EmployeeRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeRoleService {
    private final EmployeeRoleRepository employeeRoleRepository;

    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRoleRepository = employeeRoleRepository;
    }

    public List<EmployeeRole> findAll() {
        return employeeRoleRepository.findAll();
    }

    public Optional<EmployeeRole> findById(UUID id) {
        return employeeRoleRepository.findById(id);
    }

    public Optional<EmployeeRole> findByName(String name) {
        return employeeRoleRepository.findByName(name);
    }

    public EmployeeRole save(EmployeeRole role) {
        return employeeRoleRepository.save(role);
    }

    public void deleteById(UUID id) {
        employeeRoleRepository.deleteById(id);
    }

}