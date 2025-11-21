package com.example.roleservice.dto;

public class EmployeeRoleCreateDto {
    private String name;
    private String department;
    private String description;

    public EmployeeRoleCreateDto() {}

    public EmployeeRoleCreateDto(String name,  String department, String description) {
        this.name = name;
        this.department = department;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setDescription(String description) { this.description = description; }
}