package com.example.employeeservice.initializer;

import com.example.employeeservice.dto.EmployeeRoleDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.client.EmployeeRoleClient;
import com.example.employeeservice.entity.SimplifiedRole;
import com.example.employeeservice.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class DataInitializer {

    private final EmployeeService employeeService;
    private final EmployeeRoleClient employeeRoleClient;

    public DataInitializer(EmployeeService employeeService, EmployeeRoleClient employeeRoleClient) {
        this.employeeService = employeeService;
        this.employeeRoleClient = employeeRoleClient;
    }

    @PostConstruct
    public void init() {

        List<EmployeeRoleDto> roles;
        try {
            roles = employeeRoleClient.getAllEmployeeRoles();
        } catch (Exception e) {
            System.err.println("Could not fetch roles from Employee roles Service: " + e.getMessage());
            return;
        }

        Map<String, EmployeeRoleDto> roleMap = roles.stream()
                .collect(Collectors.toMap(EmployeeRoleDto::getName, role -> role));

        if (!roleMap.containsKey("Technician") || !roleMap.containsKey("Salesman") ||
                !roleMap.containsKey("Cleaner")) {
            System.err.println("Not all required albums are available in Album Service.");
            return;
        }

        EmployeeRoleDto technicianDto = roleMap.get("Technician");
        EmployeeRoleDto salesmanDto = roleMap.get("Salesman");
        EmployeeRoleDto cleanerDto = roleMap.get("Cleaner");

        SimplifiedRole technician = new SimplifiedRole(UUID.fromString(technicianDto.getId()), technicianDto.getName());
        SimplifiedRole salesman = new SimplifiedRole(UUID.fromString(salesmanDto.getId()), salesmanDto.getName());
        SimplifiedRole cleaner = new SimplifiedRole(UUID.fromString(cleanerDto.getId()), cleanerDto.getName());

        Employee e1 = new Employee.Builder()
                .name("Wojciech")
                .surname("Nieszczęsny")
                .salary(45.0)
                .phoneNumber("111222333")
                .employeeRole(cleaner)
                .build();

        Employee e2 = new Employee.Builder()
                .name("Andrzej")
                .surname("Lipny")
                .salary(60.0)
                .phoneNumber("444555666")
                .employeeRole(technician)
                .build();

        Employee e3 = new Employee.Builder()
                .name("Agata")
                .surname("Herbata")
                .salary(50.0)
                .phoneNumber("222333111")
                .employeeRole(salesman)
                .build();

        employeeService.save(e1);
        employeeService.save(e2);
        employeeService.save(e3);

        System.out.println("DataInitializer: added 3 example employees");
    }
}