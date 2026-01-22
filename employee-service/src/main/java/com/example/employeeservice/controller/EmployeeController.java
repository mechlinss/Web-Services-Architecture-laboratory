package com.example.employeeservice.controller;

import com.example.employeeservice.client.EmployeeRoleClient;
import com.example.employeeservice.dto.EmployeeListDto;
import com.example.employeeservice.dto.EmployeeRoleDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.SimplifiedRole;
import com.example.employeeservice.dto.EmployeeCreateDto;
import com.example.employeeservice.dto.EmployeeReadDto;
import com.example.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeRoleClient employeeRoleClient;

    @Value("${server.port:8082}")
    private String serverPort;

    public EmployeeController(EmployeeService employeeService, EmployeeRoleClient employeeRoleClient) {
        this.employeeService = employeeService;
        this.employeeRoleClient = employeeRoleClient;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeListDto>> listEmployees() {
        log.info("Handling request on employee-service instance at port: {}", serverPort);
        List<EmployeeListDto> employees = employeeService.findAll().stream().map(this::toEmployeeListDto).collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeReadDto> getEmployee(@PathVariable("id") UUID id) {
        log.info("Handling request on employee-service instance at port: {}", serverPort);
        return employeeService.findById(id).map(this::toEmployeeReadDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
        EmployeeRoleDto employeeRole = employeeRoleClient.getEmployeeRoleById(dto.getEmployeeRoleId());
        if (employeeRole == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Employee role not found"));
        }
        if (Objects.equals(dto.getName(), employeeRole.getName())) {
            return ResponseEntity.status(409).build();
        }

        SimplifiedRole role = new SimplifiedRole( dto.getEmployeeRoleId(), employeeRole.getName());

        Employee e = new Employee.Builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .salary(dto.getSalary())
                .phoneNumber(dto.getPhoneNumber())
                .employeeRole(role)
                .build();

        Employee saved = employeeService.save(e);
        URI location = URI.create("/api/employees/" + saved.getId());
        return ResponseEntity.created(location).body(toEmployeeReadDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeCreateDto dto) {
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EmployeeRoleDto employeeRole = employeeRoleClient.getEmployeeRoleById(dto.getEmployeeRoleId());
        if (employeeRole == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Employee role not found"));
        }

        SimplifiedRole role = new SimplifiedRole( dto.getEmployeeRoleId(), employeeRole.getName());

        Employee existing = employeeOpt.get();
        Employee updated = new Employee.Builder()
                .id(existing.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .salary(dto.getSalary())
                .phoneNumber(dto.getPhoneNumber())
                .employeeRole(role)
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

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<List<EmployeeListDto>> getEmployeesByRoleId(@PathVariable("roleId") UUID roleId) {
        List<Employee> employees = employeeService.findByEmployeeRole_Id(roleId);

        if (employees.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EmployeeListDto> employeeDtos = employees.stream()
                .map(this::toEmployeeListDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeDtos);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteEmployeesByRole(@PathVariable("roleId") UUID roleId) {
        List<Employee> employees = employeeService.findByEmployeeRole_Id(roleId);

        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        employeeService.deleteByEmployeeRoleId(roleId);

        return ResponseEntity.ok(Map.of(
                "message", "Deleted employees with role",
                "count", employees.size()
        ));
    }

    @PatchMapping("/roles/{roleId}")
    public ResponseEntity<?> updateEmployeesRole(
            @PathVariable("roleId") UUID roleId,
            @RequestBody String roleName) {

        List<Employee> employees = employeeService.findByEmployeeRole_Id(roleId);
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int updatedCount = employeeService.updateEmployeesRole(roleId, roleName);

        return ResponseEntity.ok(Map.of(
                "message", "Updated employees role",
                "count", updatedCount
        ));
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
        return new EmployeeReadDto(e.getId().toString(), e.getName(), e.getSurname(), roleName, e.getSalary(), e.getPhoneNumber());
    }
}