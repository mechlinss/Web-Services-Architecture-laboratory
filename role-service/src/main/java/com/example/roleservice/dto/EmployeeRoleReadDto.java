package com.example.roleservice.dto;

import java.util.List;

public class EmployeeRoleReadDto {
    private String id;
    private String name;
    private String department;
    private String description;

    public EmployeeRoleReadDto() {}

    public EmployeeRoleReadDto(String id, String name, String department, String description) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }
}