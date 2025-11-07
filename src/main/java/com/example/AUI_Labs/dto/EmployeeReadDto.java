package com.example.AUI_Labs.dto;

import java.util.List;
import java.util.stream.Collector;

/**
 * Szczegółowe DTO dla pojedynczego pracownika.
 */
public class EmployeeReadDto {
    private String id;
    private String name;
    private String surname;
    private String roleName;

    public EmployeeReadDto() {}

    public EmployeeReadDto(String id, String name, String surname, String roleName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.roleName = roleName;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRoleName() { return roleName; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public Object collect(Collector<Object,?, List<Object>> list) {
    }
}