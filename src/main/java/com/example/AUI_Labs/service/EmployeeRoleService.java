package com.example.AUI_Labs.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.AUI_Labs.entity.EmployeeRole;
import com.example.AUI_Labs.repository.EmployeeRoleRepository;
import org.springframework.stereotype.Service;

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

    public List<EmployeeRole> findBySalary(Float salary) {
        return employeeRoleRepository.findBySalary(salary);
    }

    public EmployeeRole save(EmployeeRole role) {
        return employeeRoleRepository.save(role);
    }
    public void deleteById(UUID id) {
        employeeRoleRepository.deleteById(id);
    }

}