package com.example.AUI_Labs.model;

public class EmployeeDto implements Comparable<EmployeeDto> {
    private final String id;
    private final String name;
    private final String surname;
    private String role;

    public static class Builder {
        private String id;
        private String name;
        private String surname;
        private String role;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder surname(String surname) { this.surname = surname; return this; }
        public Builder role(String role) { this.role = role; return this; }

        public EmployeeDto build() {
            return new EmployeeDto(id, name, surname, role);
        }
    }

    public static Builder builder() { return new Builder(); }

    private EmployeeDto(String id, String name, String surname, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    @Override
    public int compareTo(EmployeeDto o) {
        int compare = this.surname.compareTo(o.surname);
        if (compare != 0){
            return compare;
        }
        compare = this.name.compareTo(o.name);
        return (compare != 0) ? compare : this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return surname + " " + name + " | ID: " + id + " | Role: " + role;
    }
}
