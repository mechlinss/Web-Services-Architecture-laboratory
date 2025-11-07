package com.example.AUI_Labs.dto;

public class RoleUpdateDto {
    private String name;

    public RoleUpdateDto() {}

    public RoleUpdateDto(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
