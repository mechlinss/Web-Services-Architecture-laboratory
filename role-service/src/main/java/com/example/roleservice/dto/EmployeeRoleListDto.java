package com.example.roleservice.dto;

public class EmployeeRoleListDto {
    private String id;
    private String name;

    public EmployeeRoleListDto() {}

    public EmployeeRoleListDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}
