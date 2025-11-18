package com.example.AUI_Labs.dto;

import java.util.List;

public class EmployeeRoleReadDto {
    private String id;
    private String name;
    private String department;
    private String description;
    private List<String> employees;

    public EmployeeRoleReadDto() {}

    public EmployeeRoleReadDto(String id, String name, String department, String description,  List<String> employees) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.description = description;
        this.employees = employees;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }
    public List<String> getEmployees() { return employees; }
}