package com.example.AUI_Labs;

import java.util.Objects;

public class Employee implements Comparable<Employee> {

    private final String id;
    private final String name;
    private final String surname;
    private EmployeeRole role;

    public static class Builder {
        private String id;
        private String name;
        private String surname;
        private EmployeeRole role;

        public Builder id(String id) {this.id = id; return this;}
        public Builder name(String name) {this.name = name; return this;}
        public Builder surname(String surname) {this.surname = surname; return this;}
        public Builder role(EmployeeRole role) {this.role = role; return this;}

        public Employee build() {
            Employee employee = new Employee(id, name, surname, role);
            if (!Objects.isNull(role)) role.addEmployee(employee);
            return employee;
        }
    }

    public static Builder builder() {return new Builder();}

    private Employee(String id, String name, String surname, EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    void setRole(EmployeeRole role) {
        this.role = role;
    }

    public EmployeeRole getRole() {
        return this.role;
    }
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Employee o){
        int compare = this.surname.compareTo(o.surname);
        if (compare != 0){
            return compare;
        }
        compare = this.name.compareTo(o.name);
        return (compare != 0) ? compare : this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return surname + " " + name + " | ID: " + id + " | Role: " + role.getName();
    }

    public EmployeeDto toDto() {
        return EmployeeDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .role(role != null ? role.getName() : null)
                .build();
    }
}
