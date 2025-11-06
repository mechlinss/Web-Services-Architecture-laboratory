package com.example.AUI_Labs.service;

import com.example.AUI_Labs.model.EmployeeRole;
import com.example.AUI_Labs.repository.EmployeeRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeRoleService {

    private final EmployeeRoleRepository repo;

    public EmployeeRoleService(EmployeeRoleRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public List<EmployeeRole> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Optional<EmployeeRole> findById(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public Optional<EmployeeRole> findByName(String name) {
        return repo.findByName(name);
    }

    @Transactional
    public EmployeeRole save(EmployeeRole role) {
        return repo.save(role);
    }

    @Transactional
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}