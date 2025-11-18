package com.example.AUI_Labs.initializer;

import com.example.AUI_Labs.entity.Employee;
import com.example.AUI_Labs.entity.EmployeeRole;
import com.example.AUI_Labs.service.EmployeeRoleService;
import com.example.AUI_Labs.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer {

    private final EmployeeRoleService roleService;
    private final EmployeeService employeeService;

    public DataInitializer(EmployeeRoleService roleService, EmployeeService employeeService) {
        this.roleService = roleService;
        this.employeeService = employeeService;
    }

    @PostConstruct
    public void init() {
        EmployeeRole salesman = new EmployeeRole.Builder().name("Salesman").department("Reception").description("Responsible for client service").build();
        EmployeeRole cleaner = new EmployeeRole.Builder().name("Cleaner").department("Everywhere").description("Responsible for keeping the workspace clean").build();
        EmployeeRole technician = new EmployeeRole.Builder().name("Technician").department("Workshop").description("Responsible for repairing broken stuff").build();

        roleService.save(salesman);
        roleService.save(cleaner);
        roleService.save(technician);

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