package com.example.AUI_Labs.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "employee_roles")
public class EmployeeRole implements Comparable<EmployeeRole>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "employeeRole", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    protected EmployeeRole() {}

    private EmployeeRole(Builder b) {
        this.id = b.id != null ? b.id : UUID.randomUUID();
        this.name = b.name;
        this.description = b.description;
        this.department = b.department;
        if (b.employees != null) {
            this.employees = b.employees;
            for (Employee e : this.employees) {
                e.setEmployeeRole(this);
            }
        }
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDepartment() { return department; }
    public List<Employee> getEmployees() { return Collections.unmodifiableList(employees); }

    public void addEmployee(Employee e) {
        if (e == null) return;
        if (!employees.contains(e)) {
            employees.add(e);
            e.setEmployeeRole(this);
        }
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String description;
        private String department;
        private List<Employee> employees;

        public Builder id(UUID id){ this.id = id; return this; }
        public Builder name(String name){ this.name = name; return this; }
        public Builder description(String description){ this.description = description; return this; }
        public Builder department(String department){ this.department = department; return this; }
        public Builder employees(List<Employee> employees){ this.employees = employees; return this; }
        public EmployeeRole build(){ return new EmployeeRole(this); }
    }

    @Override
    public int compareTo(EmployeeRole o) {
        if (o == null) return 1;
        return this.name.compareToIgnoreCase(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeRole)) return false;
        EmployeeRole that = (EmployeeRole) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee role: " + name + " | " + id + " | department: " + department  + " | description: " + description;
    }
}