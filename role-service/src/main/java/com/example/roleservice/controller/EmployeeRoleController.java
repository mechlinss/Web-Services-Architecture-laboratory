package com.example.roleservice.controller;

import com.example.roleservice.client.EmployeeClient;
import com.example.roleservice.dto.EmployeeDto;
import com.example.roleservice.dto.EmployeeRoleCreateDto;
import com.example.roleservice.dto.EmployeeRoleListDto;
import com.example.roleservice.dto.EmployeeRoleReadDto;
import com.example.roleservice.entity.EmployeeRole;
import com.example.roleservice.service.EmployeeRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-roles")
public class EmployeeRoleController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRoleController.class);

    private final EmployeeRoleService employeeRoleService;
    private final EmployeeClient employeeClient;

    @Value("${server.port:8081}")
    private String serverPort;

    public EmployeeRoleController(EmployeeRoleService employeeRoleService, EmployeeClient employeeClient) {
        this.employeeRoleService = employeeRoleService;
        this.employeeClient = employeeClient;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeRoleListDto>> listEmployeeRoles() {
        log.info("Handling request on role-service instance at port: {}", serverPort);
        List<EmployeeRoleListDto> list = employeeRoleService.findAll()
                .stream()
                .map(this::toEmployeeRoleListDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRoleReadDto> getEmployeeRole(@PathVariable("id") UUID id) {
        log.info("Handling request on role-service instance at port: {}", serverPort);
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

            try {
                employeeClient.updateEmployeesRole(id, dto.getName());
            } catch (Exception e){
                System.err.println("Error updating employees for role " + id + ": " + e.getMessage());
            }

            existing = new EmployeeRole.Builder()
                    .id(existing.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .department(dto.getDepartment())
                    .build();
            EmployeeRole saved = employeeRoleService.save(existing);
            return ResponseEntity.ok(toEmployeeRoleReadDto(saved));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeRole(@PathVariable("id") UUID id) {
        return employeeRoleService.findById(id).map(r -> {
            try {
                employeeClient.deleteEmployeesByEmployeeRoleId(id);
            } catch (Exception e) {
                System.err.println("Error deleting employees for role " + id + ": " + e.getMessage());
            }
            employeeRoleService.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private EmployeeRoleListDto toEmployeeRoleListDto(EmployeeRole role) {
        return new EmployeeRoleListDto(role.getId().toString(), role.getName());
    }

    private EmployeeRoleReadDto toEmployeeRoleReadDto(EmployeeRole role) {
        return new EmployeeRoleReadDto(
                role.getId().toString(),
                role.getName(),
                role.getDepartment(),
                role.getDescription()
        );
    }
}