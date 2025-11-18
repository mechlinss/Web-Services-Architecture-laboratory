package com.example.AUI_Labs.controller;

import com.example.AUI_Labs.dto.EmployeeListDto;
import com.example.AUI_Labs.entity.Employee;
import com.example.AUI_Labs.entity.EmployeeRole;
import com.example.AUI_Labs.dto.EmployeeCreateDto;
import com.example.AUI_Labs.dto.EmployeeReadDto;
import com.example.AUI_Labs.service.EmployeeRoleService;
import com.example.AUI_Labs.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRoleService employeeRoleService;

    public EmployeeController(EmployeeService employeeService, EmployeeRoleService roleService) {
        this.employeeService = employeeService;
        this.employeeRoleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeListDto>> listEmployees() {
        List<EmployeeListDto> employees = employeeService.findAll().stream().map(this::toEmployeeListDto).collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeReadDto> getEmployee(@PathVariable("id") UUID id) {
        return employeeService.findById(id).map(this::toEmployeeReadDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
        Optional<EmployeeRole> roleOpt = employeeRoleService.findById(UUID.fromString(dto.getEmployeeRoleId()));

        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Employee role not found"));
        }

        Employee e = new Employee.Builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .salary(dto.getSalary())
                .phoneNumber(dto.getPhoneNumber())
                .employeeRole(roleOpt.get())
                .build();

        Employee saved = employeeService.save(e);
        URI location = URI.create("/api/employees/" + saved.getId());
        return ResponseEntity.created(location).body(toEmployeeReadDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeReadDto> updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeCreateDto dto) {
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<EmployeeRole> roleOpt = employeeRoleService.findById(
                UUID.fromString(dto.getEmployeeRoleId()));
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Employee existing = employeeOpt.get();
        Employee updated = new Employee.Builder()
                .id(existing.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .salary(dto.getSalary())
                .phoneNumber(dto.getPhoneNumber())
                .employeeRole(roleOpt.get())
                .build();

        Employee saved = employeeService.save(updated);
        return ResponseEntity.ok(toEmployeeReadDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") UUID id) {
        return employeeService.findById(id).map(e -> {
            employeeService.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
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

    private EmployeeReadDto toEmployeeReadDto(Employee e) {
        String roleName = e.getEmployeeRole() != null ?  e.getEmployeeRole().getName() : null;
        return new EmployeeReadDto(e.getEmployeeRole().getId().toString(), e.getName(), e.getSurname(), roleName, e.getSalary(), e.getPhoneNumber());
    }
}