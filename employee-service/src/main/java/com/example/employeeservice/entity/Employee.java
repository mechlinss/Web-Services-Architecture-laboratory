package com.example.employeeservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee implements Comparable<Employee>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Embedded
    private SimplifiedRole employeeRole;

    public Employee() {}

    private Employee(Builder b) {
        this.id = b.id != null ? b.id : UUID.randomUUID();
        this.name = b.name;
        this.surname = b.surname;
        this.salary = b.salary;
        this.phoneNumber = b.phoneNumber;
        this.employeeRole = b.employeeRole;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Double getSalary() { return salary; }
    public String getPhoneNumber() { return phoneNumber; }
    public SimplifiedRole getEmployeeRole() { return employeeRole; }

    void setName(final String name) { this.name = name; }
    void setSurname(final String surname) { this.surname = surname; }
    void setSalary(final Double salary) { this.salary = salary; }
    void setPhoneNumber(final String phoneNumber) { this.phoneNumber = phoneNumber; }
    void setEmployeeRole(SimplifiedRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String surname;
        private Double salary;
        private String phoneNumber;
        private SimplifiedRole employeeRole;

        public Builder id(UUID id){ this.id = id; return this; }
        public Builder name(String name){ this.name = name; return this; }
        public Builder surname(String surname){ this.surname = surname; return this; }
        public Builder salary(Double salary){ this.salary = salary; return this; }
        public Builder phoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; return this; }
        public Builder employeeRole(SimplifiedRole employeeRole){ this.employeeRole = employeeRole; return this; }
        public Employee build(){ return new Employee(this); }
    }

    @Override
    public int compareTo(Employee e) {
        if (e == null) return 1;
        int cmp = this.name.compareToIgnoreCase(e.name);
        if (cmp != 0) return cmp;
        return this.surname.compareToIgnoreCase(e.surname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee that = (Employee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee: " + name + " " + surname + " | " + id + " | " + employeeRole.toString() + " | phone number: " + phoneNumber + " | salary: " + salary;
    }
}