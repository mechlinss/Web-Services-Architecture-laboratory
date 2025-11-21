package com.example.employeeservice.dto;

import java.util.List;
import java.util.stream.Collector;

public class EmployeeReadDto {
    private String id;
    private String name;
    private String surname;
    private Double salary;
    private String phoneNumber;
    private String roleName;

    public EmployeeReadDto() {}

    public EmployeeReadDto(String id, String name, String surname, String roleName, Double salary, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Double getSalary() { return salary; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRoleName() { return roleName; }
}