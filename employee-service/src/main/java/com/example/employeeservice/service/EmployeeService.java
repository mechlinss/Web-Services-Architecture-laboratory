package com.example.employeeservice.service;

import com.example.employeeservice.entity.*;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByEmployeeRole_Id(UUID id) {
        return employeeRepository.findByEmployeeRoleId(id);
    }

    public List<Employee> findByEmployeeRoleName(String name) {
        return employeeRepository.findByEmployeeRole_Name(name);
    }

    public int deleteByEmployeeRoleId(UUID roleId) {
        List<Employee> employees = employeeRepository.findByEmployeeRoleId(roleId);
        employees.forEach(e -> employeeRepository.deleteById(e.getId()));
        return employees.size();
    }

    public int updateEmployeesRole(UUID roleId, String newRoleName) {
        List<Employee> employees = employeeRepository.findByEmployeeRoleId(roleId);

        SimplifiedRole newRole = new SimplifiedRole(roleId, newRoleName);

        employees.forEach(employee -> {
            Employee updated = new Employee.Builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .surname(employee.getSurname())
                    .salary(employee.getSalary())
                    .phoneNumber(employee.getPhoneNumber())
                    .employeeRole(newRole)
                    .build();
            employeeRepository.save(updated);
        });

        return employees.size();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

}