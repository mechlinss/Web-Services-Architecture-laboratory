package com.example.AUI_Labs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeRole implements Serializable, Comparable<EmployeeRole>{
    private final String name;
    private final List<Employee> employees = new ArrayList();

    public static class Builder {
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public EmployeeRole build() {
            return new EmployeeRole(name);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    private EmployeeRole(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        if (employee == null) return;
        if (!employees.contains(employee)) employees.add(employee);
        if (employee.getRole() != this) employee.setRole(this);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public String getName() {return name;}

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

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
