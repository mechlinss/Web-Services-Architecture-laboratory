package com.example.AUI_Labs.dto;


public class EmployeeCreateDto {
    private String name;
    private String surname;
    private Double salary;
    private String phoneNumber;
    private String employeeRoleId;

    public EmployeeCreateDto() {}

    public EmployeeCreateDto(String name, String surname, Double salary, String phoneNumber, String employeeRoleId) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.employeeRoleId = employeeRoleId;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Double getSalary() { return salary; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmployeeRoleId() { return employeeRoleId; }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setSalary(Double salary) { this.salary = salary; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmployeeRoleId(String employeeRoleId) { this.employeeRoleId = employeeRoleId; }
}