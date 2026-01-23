package com.example.employeeservice.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Embeddable
public class SimplifiedRole {

    @Column(name = "employee_role_id")
    private UUID id;

    @Column(name = "employee_role_name")
    private String name;

    public SimplifiedRole() {}

    public SimplifiedRole(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}