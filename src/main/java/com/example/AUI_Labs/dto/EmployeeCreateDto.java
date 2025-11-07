package com.example.AUI_Labs.dto;

/**
 * Do tworzenia/aktualizacji pracownika. Nie zawiera pola kategorii (rola),
 * bo role jest przekazywana w ścieżce REST: POST /api/roles/{roleId}/employees
 */
public class EmployeeCreateDto {
    private String name;
    private String surname;

    public EmployeeCreateDto() {}

    public EmployeeCreateDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
}