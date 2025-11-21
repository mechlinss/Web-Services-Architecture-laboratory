package com.example.roleservice.dto;

public class EmployeeDto {
    private String id;
    private String name;
    private String surname;
    private String roleId;
    private String roleName;

    public EmployeeDto() {}

    public EmployeeDto(String id, String name, String surname, String roleId, String roleName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRoleName() { return roleName; }
    public String getRoleId() { return roleId; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setRoleId(String roleId) { this.roleId = roleId; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
