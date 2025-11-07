package com.example.AUI_Labs.controller;

import com.example.AUI_Labs.entity.Employee;
import com.example.AUI_Labs.entity.EmployeeRole;
import com.example.AUI_Labs.dto.EmployeeCreateDto;
import com.example.AUI_Labs.dto.EmployeeReadDto;
import com.example.AUI_Labs.dto.EmployeeSummaryDto;
import com.example.AUI_Labs.service.EmployeeRoleService;
import com.example.AUI_Labs.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRoleService roleService;

    public EmployeeController(EmployeeService employeeService, EmployeeRoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeReadDto>> getAllEmployees() {
        List<EmployeeReadDto> employeeReadDtoList = employeeService.findAll().stream()
                .map(e -> new EmployeeReadDto(e.getId().toString(), e.getName(), e.getSurname(), e.getEmployeeRole().getName())
                .collect(Collectors.toList()));

        return ResponseEntity.ok(employeeReadDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeReadDto> getEmployee(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return employeeService.findById(uuid)
                    .map(e -> {
                        String roleId = e.getRole() != null ? e.getRole().getId() : null;
                        String roleName = e.getRole() != null ? e.getRole().getName() : null;
                        return ResponseEntity.ok(new EmployeeReadDto(e.getId(), e.getName(), e.getSurname(), roleId, roleName));
                    }).orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Create employee under given role:
     * POST /api/roles/{roleId}/employees
     * returns 201 with Location header /api/employees/{id}
     */
    @PostMapping(path = "/by-role/{roleId}")
    public ResponseEntity<EmployeeReadDto> createEmployeeUnderRole(@PathVariable String roleId, @RequestBody EmployeeCreateDto dto) {
        try {
            UUID rid = UUID.fromString(roleId);
            if (!roleService.findById(rid).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Employee saved = employeeService.createEmployee(dto.getName(), dto.getSurname(), rid);
            String location = "/api/employees/" + saved.getId();
            return ResponseEntity.created(URI.create(location))
                    .body(new EmployeeReadDto(saved.getId(), saved.getName(), saved.getSurname(), saved.getRole() != null ? saved.getRole().getId() : null, saved.getRole() != null ? saved.getRole().getName() : null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeReadDto> updateEmployee(@PathVariable String id, @RequestBody EmployeeCreateDto dto) {
        try {
            UUID uid = UUID.fromString(id);
            return employeeService.findById(uid).map(existing -> {
                existing.setName(dto.getName());
                existing.setSurname(dto.getSurname());
                Employee saved = employeeService.save(existing);
                String roleId = saved.getRole() != null ? saved.getRole().getId() : null;
                String roleName = saved.getRole() != null ? saved.getRole().getName() : null;
                return ResponseEntity.ok(new EmployeeReadDto(saved.getId(), saved.getName(), saved.getSurname(), roleId, roleName));
            }).orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Move employee to other role: PUT /api/employees/{id}/role/{roleId}
     * (alternative: PUT /api/roles/{roleId}/employees/{id})
     */
    @PutMapping("/{id}/role/{roleId}")
    public ResponseEntity<EmployeeReadDto> moveEmployeeToRole(@PathVariable String id, @PathVariable String roleId) {
        try {
            UUID uid = UUID.fromString(id);
            UUID rid = UUID.fromString(roleId);
            var empOpt = employeeService.findById(uid);
            var roleOpt = roleService.findById(rid);
            if (empOpt.isEmpty()) return ResponseEntity.notFound().build();
            if (roleOpt.isEmpty()) return ResponseEntity.status(404).build();
            Employee emp = empOpt.get();
            EmployeeRole newRole = roleOpt.get();
            emp.setRole(newRole);
            Employee saved = employeeService.save(emp);
            return ResponseEntity.ok(new EmployeeReadDto(saved.getId(), saved.getName(), saved.getSurname(), newRole.getId(), newRole.getName()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        try {
            UUID uid = UUID.fromString(id);
            if (employeeService.findById(uid).isEmpty()) return ResponseEntity.notFound().build();
            employeeService.deleteById(uid);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}