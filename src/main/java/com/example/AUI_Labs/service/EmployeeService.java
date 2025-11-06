package com.example.AUI_Labs.service;

import com.example.AUI_Labs.entity.*;
import com.example.AUI_Labs.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByRole(UUID id) {
        return employeeRepository.findByEmployeeRole_Id(id);
    }

    public List<Employee> findByRoleName(String name) {
        return employeeRepository.findByEmployeeRole_Name(name);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

}