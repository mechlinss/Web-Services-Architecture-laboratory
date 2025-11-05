package com.example.AUI_Labs;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "employee_roles")
public class EmployeeRole implements Serializable, Comparable<EmployeeRole> {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private final List<Employee> employees = new ArrayList<>();

    // JPA
    protected EmployeeRole() {
    }

    private EmployeeRole(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class Builder {
        private UUID id;
        private String name;

        public Builder id(String id) {
            if (id != null && !id.isBlank()) this.id = UUID.fromString(id);
            return this;
        }

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }

        public EmployeeRole build() {
            UUID finalId = this.id != null ? this.id : UUID.randomUUID();
            return new EmployeeRole(finalId, name);
        }
    }

    public static Builder builder() { return new Builder(); }

    public void addEmployee(Employee employee) {
        if (employee == null) return;
        if (!employees.contains(employee)) employees.add(employee);
        if (employee.getRole() != this) employee.setRole(this);
    }

    public List<Employee> getEmployees() { return employees; }
    public String getName() { return name; }
    public UUID getUuid() { return id; }
    public String getId() { return id != null ? id.toString() : null; }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmployeeRole)) return false;
        EmployeeRole other = (EmployeeRole) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int compareTo(EmployeeRole other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Role: " + name + " | employees: " + employees.size();
    }
}