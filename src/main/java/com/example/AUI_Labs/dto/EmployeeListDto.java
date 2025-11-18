package com.example.AUI_Labs.dto;

public class EmployeeListDto {
    private String id;
    private String name;
    private String surname;
    private String roleName;

    public EmployeeListDto() {}

    public EmployeeListDto(String id, String name, String surname, String roleName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.roleName = roleName;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRoleName() { return roleName; }
}
