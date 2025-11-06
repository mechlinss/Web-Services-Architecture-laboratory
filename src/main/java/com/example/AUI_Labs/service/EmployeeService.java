package com.example.AUI_Labs.service;

import com.example.AUI_Labs.model.Employee;
import com.example.AUI_Labs.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> findAll() {
        return repo.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return repo.findById(id);
    }

    public void save(Employee employee) {
        repo.save(employee);
    }

    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Transactional
    public List<Employee> findByRoleId(UUID roleId) {
        return repo.findByRole_Id(roleId);
    }

    @Transactional
    public List<Employee> findByRoleName(String roleName) {
        return repo.findByRole_Name(roleName);
    }
}