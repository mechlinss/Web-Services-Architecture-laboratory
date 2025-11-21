package com.example.roleservice.initializer;

import com.example.roleservice.entity.EmployeeRole;
import com.example.roleservice.service.EmployeeRoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer {

    private final EmployeeRoleService roleService;

    public DataInitializer(EmployeeRoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        EmployeeRole salesman = new EmployeeRole.Builder().name("Salesman").department("Reception").description("Responsible for client service").build();
        EmployeeRole cleaner = new EmployeeRole.Builder().name("Cleaner").department("Everywhere").description("Responsible for keeping the workspace clean").build();
        EmployeeRole technician = new EmployeeRole.Builder().name("Technician").department("Workshop").description("Responsible for repairing broken stuff").build();

        roleService.save(salesman);
        roleService.save(cleaner);
        roleService.save(technician);
    }
}