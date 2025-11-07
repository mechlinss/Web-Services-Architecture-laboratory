package com.example.AUI_Labs.dto;

public class RoleCreateDto {
    private String name;

    public RoleCreateDto() {}

    public RoleCreateDto(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}