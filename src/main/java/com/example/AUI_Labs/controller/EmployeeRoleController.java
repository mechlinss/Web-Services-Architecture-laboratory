package com.example.AUI_Labs.controller;

import com.example.AUI_Labs.entity.*;
import com.example.AUI_Labs.dto.*;
import com.example.AUI_Labs.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-roles")
public class EmployeeRoleController {

    private final EmployeeRoleService employeeRoleService;
    private final EmployeeService employeeService;

    public EmployeeRoleController(EmployeeRoleService employeeRoleService, EmployeeService employeeService) {
        this.employeeRoleService = employeeRoleService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeRoleListDto>> listEmployeeRoles() {
        List<EmployeeRoleListDto> list = employeeRoleService.findAll()
                .stream()
                .map(this::toEmployeeRoleListDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRoleReadDto> getEmployeeRole(@PathVariable("id") UUID id) {
        return employeeRoleService.findById(id)
                .map(this::toEmployeeRoleReadDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeRoleReadDto> createEmployeeRole(@RequestBody EmployeeRoleCreateDto dto) {
        EmployeeRole role = new EmployeeRole.Builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .department(dto.getDepartment())
                .build();
        EmployeeRole saved = employeeRoleService.save(role);
        EmployeeRoleReadDto read = toEmployeeRoleReadDto(saved);
        URI location = URI.create("/api/employee-roles/" + saved.getId());
        return ResponseEntity.created(location).body(read);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRoleReadDto> updateEmployeeRole(@PathVariable("id") UUID id, @RequestBody EmployeeRoleCreateDto dto) {
        return employeeRoleService.findById(id).map(existing -> {
            EmployeeRole updated = new EmployeeRole.Builder()
                    .id(existing.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .department(dto.getDepartment())
                    .employees(existing.getEmployees())
                    .build();
            EmployeeRole saved = employeeRoleService.save(updated);
            return ResponseEntity.ok(toEmployeeRoleReadDto(saved));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeRole(@PathVariable("id") UUID id) {
        return employeeRoleService.findById(id).map(role -> {
            employeeRoleService.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeListDto>> listEmployeesForRole(@PathVariable("id") UUID id) {
        return employeeRoleService.findById(id).map(role -> {
            List<EmployeeListDto> employees = employeeService.findByEmployeeRole_Id(role.getId()).stream()
                    .map(this::toEmployeeListDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(employees);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private EmployeeRoleListDto toEmployeeRoleListDto(EmployeeRole role) {
        return new EmployeeRoleListDto(role.getId().toString(), role.getName());
    }

    private EmployeeRoleReadDto toEmployeeRoleReadDto(EmployeeRole role) {
        List<String> employeeNames = role.getEmployees().stream()
                .map(e -> e.getName() + " " + e.getSurname())
                .toList();
        return new EmployeeRoleReadDto(
                role.getId().toString(),
                role.getName(),
                role.getDepartment(),
                role.getDescription(),
                employeeNames
        );
    }

    private EmployeeListDto toEmployeeListDto(Employee e) {
        String roleName = e.getEmployeeRole() != null ? e.getEmployeeRole().getName() : null;
        return new EmployeeListDto(
                e.getId().toString(),
                e.getName(),
                e.getSurname(),
                roleName
        );
    }
}