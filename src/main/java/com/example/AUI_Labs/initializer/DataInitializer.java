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
        EmployeeRole salesman = new EmployeeRole.Builder().name("Salesman").salary(50.0f).build();
        EmployeeRole cleaner = new EmployeeRole.Builder().name("Cleaner").salary(40.0f).build();
        EmployeeRole technician = new EmployeeRole.Builder().name("Technician").salary(60.0f).build();

        roleService.save(salesman);
        roleService.save(cleaner);
        roleService.save(technician);

        Employee e1 = new Employee.Builder()
                .name("Wojciech")
                .surname("Nieszczęsny")
                .employeeRole(cleaner)
                .build();

        Employee e2 = new Employee.Builder()
                .name("Andrzej")
                .surname("Lipny")
                .employeeRole(technician)
                .build();

        Employee e3 = new Employee.Builder()
                .name("Agata")
                .surname("Herbata")
                .employeeRole(salesman)
                .build();

        employeeService.save(e1);
        employeeService.save(e2);
        employeeService.save(e3);

        System.out.println("DataInitializer: added 3 example employees");
    }
}