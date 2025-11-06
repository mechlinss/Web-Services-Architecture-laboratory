package com.example.AUI_Labs.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "employees")
public class Employee implements Serializable, Comparable<Employee> {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private EmployeeRole role;

    // JPA
    protected Employee() {
    }

    private Employee(UUID id, String name, String surname, EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String surname;
        private EmployeeRole role;

        public Builder id(String id) {
            if (id != null && !id.isBlank()) {
                this.id = UUID.fromString(id);
            }
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) { this.name = name; return this; }
        public Builder surname(String surname) { this.surname = surname; return this; }
        public Builder role(EmployeeRole role) { this.role = role; return this; }

        public Employee build() {
            UUID finalId = this.id != null ? this.id : UUID.randomUUID();
            Employee employee = new Employee(finalId, name, surname, role);
            if (role != null) role.addEmployee(employee);
            return employee;
        }
    }

    public static Builder builder() { return new Builder(); }

    void setRole(EmployeeRole role) {
        this.role = role;
    }

    public EmployeeRole getRole() { return this.role; }
    public UUID getUuid() { return this.id; }
    public String getId() { return id != null ? id.toString() : null; }
    public String getName() { return this.name; }
    public String getSurname() { return this.surname; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public int compareTo(Employee o){
        int compare = this.surname.compareTo(o.surname);
        if (compare != 0) return compare;
        compare = this.name.compareTo(o.name);
        return (compare != 0) ? compare : this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return surname + " " + name + " | ID: " + (id != null ? id.toString() : "null") + " | Role: " + (role != null ? role.getName() : "null");
    }

    public EmployeeDto toDto() {
        return EmployeeDto.builder()
                .id(getId())
                .name(name)
                .surname(surname)
                .role(role != null ? role.getName() : null)
                .build();
    }
}