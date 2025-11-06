package com.example.AUI_Labs.cli;

import com.example.AUI_Labs.model.Employee;
import com.example.AUI_Labs.model.EmployeeRole;
import com.example.AUI_Labs.service.EmployeeRoleService;
import com.example.AUI_Labs.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final EmployeeRoleService roleService;

    public ConsoleRunner(EmployeeService employeeService, EmployeeRoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.print("> ");
            String line;
            try {
                line = scanner.nextLine();
            } catch (Exception e) {
                break;
            }
            if (line == null) break;
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            String[] parts = trimmed.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "help":
                        printHelp();
                        break;
                    case "list-roles":
                        listRoles();
                        break;
                    case "list-employees":
                        listEmployees();
                        break;
                    case "list-employees-by-role":
                        if (parts.length < 2) {
                            System.out.println("Usage: list-employees-by-role <role-uuid>");
                        } else {
                            listEmployeesByRole(parts[1]);
                        }
                        break;
                    case "list-employees-by-role-name":
                        if (parts.length < 2) {
                            System.out.println("Usage: list-employees-by-role-name <role-name>");
                        } else {
                            listEmployeesByRoleName(parts[1]);
                        }
                        break;
                    case "add-employee":
                        addEmployeeInteractive(scanner);
                        break;
                    case "delete-employee":
                        if (parts.length < 2) {
                            System.out.println("Usage: delete-employee <employee-uuid>");
                        } else {
                            deleteEmployee(parts[1]);
                        }
                        break;
                    case "add-role":
                        addRoleInteractive(scanner);
                        break;
                    case "delete-role":
                        if (parts.length < 2) {
                            System.out.println("Usage: delete-role <role-uuid>");
                        } else {
                            deleteRole(parts[1]);
                        }
                        break;
                    case "exit":
                        System.out.println("Stopping application...");
                        scanner.close();
                        System.exit(0);
                        return;
                    default:
                        System.out.println("Unknown command. Type 'help' for list of commands.");
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  help                                  - show this help");
        System.out.println("  list-roles                            - list all categories (roles)");
        System.out.println("  list-employees                        - list all elements (employees)");
        System.out.println("  list-employees-by-role <id>           - list employees in given role (by UUID)");
        System.out.println("  list-employees-by-role-name <name>    - list employees in given role (by name)");
        System.out.println("  add-employee                          - interactive add new employee");
        System.out.println("  delete-employee <id>                  - delete employee by UUID");
        System.out.println("  add-role                              - interactive add new role (category)");
        System.out.println("  delete-role <id>                      - delete role by UUID");
        System.out.println("  exit                                  - stop the application");
    }

    private void listRoles() {
        List<EmployeeRole> roles = roleService.findAll();
        if (roles.isEmpty()) {
            System.out.println("No roles found.");
            return;
        }
        for (EmployeeRole r : roles) {
            String id = r.getId();
            int size = r.getEmployees() != null ? r.getEmployees().size() : 0;
            System.out.println(id + " | " + r.getName() + " | employees: " + size);
        }
    }

    private void listEmployees() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee e : employees) {
            String id = e.getId();
            String roleName = (e.getRole() != null) ? e.getRole().getName() : "null";
            System.out.println(id + " | " + e.getSurname() + " " + e.getName() + " | role: " + roleName);
        }
    }

    private void listEmployeesByRole(String roleIdStr) {
        try {
            UUID roleId = UUID.fromString(roleIdStr.trim());
            List<Employee> employees = employeeService.findByRoleId(roleId);
            if (employees.isEmpty()) {
                System.out.println("No employees found for role " + roleId);
                return;
            }
            for (Employee e : employees) {
                System.out.println(e.getId() + " | " + e.getSurname() + " " + e.getName());
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid UUID format: " + roleIdStr);
        }
    }

    private void listEmployeesByRoleName(String roleNameStr) {
        try {
            List<Employee> employees = employeeService.findByRoleName(roleNameStr.trim());
            if (employees.isEmpty()) {
                System.out.println("No employees found for role " + roleNameStr);
                return;
            }
            for (Employee e : employees) {
                System.out.println(e.getId() + " | " + e.getSurname() + " " + e.getName());
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid role name: " + roleNameStr);
        }
    }

    private void addEmployeeInteractive(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Surname: ");
        String surname = scanner.nextLine().trim();

        List<EmployeeRole> roles = roleService.findAll();
        if (roles.isEmpty()) {
            System.out.println("No roles available. Create roles first (DataInitializer should create defaults).");
            return;
        }

        System.out.println("Choose role by index:");
        for (int i = 0; i < roles.size(); i++) {
            EmployeeRole r = roles.get(i);
            System.out.println(i + ": " + r.getName() + " (" + r.getId() + ")");
        }
        System.out.print("Role index: ");
        String idxLine = scanner.nextLine().trim();
        int idx;
        try {
            idx = Integer.parseInt(idxLine);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number");
            return;
        }
        if (idx < 0 || idx >= roles.size()) {
            System.out.println("Invalid index");
            return;
        }

        EmployeeRole chosen = roles.get(idx);
        Employee newEmp = Employee.builder()
                .id(UUID.randomUUID())
                .name(name)
                .surname(surname)
                .role(chosen)
                .build();
        employeeService.save(newEmp);
        System.out.println("Added employee: " + newEmp.getId());
    }

    private void deleteEmployee(String idStr) {
        try {
            UUID id = UUID.fromString(idStr.trim());
            Optional<Employee> opt = employeeService.findById(id);
            if (opt.isEmpty()) {
                System.out.println("Employee not found: " + idStr);
                return;
            }
            employeeService.deleteById(id);
            System.out.println("Deleted employee: " + idStr);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid UUID format: " + idStr);
        }
    }

    private void addRoleInteractive(Scanner scanner) {
        System.out.print("Role name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return;
        }

        Optional<EmployeeRole> existing = roleService.findByName(name);
        if (existing.isPresent()) {
            System.out.println("Role with name '" + name + "' already exists with id " + existing.get().getId());
            return;
        }
        EmployeeRole role = EmployeeRole.builder()
                .id(UUID.randomUUID())
                .name(name)
                .build();
        roleService.save(role);
        System.out.println("Added role: " + role.getId() + " | " + role.getName());
    }

    private void deleteRole(String idStr) {
        try {
            UUID id = UUID.fromString(idStr.trim());
            Optional<EmployeeRole> opt = roleService.findById(id);
            if (opt.isEmpty()) {
                System.out.println("Role not found: " + idStr);
                return;
            }

            EmployeeRole role = opt.get();
            int count = role.getEmployees() != null ? role.getEmployees().size() : 0;
            if (count > 0) {
                System.out.println("Warning: role has " + count + " employees. Deleting role will set their role to null or cascade depending on mapping.");
            }
            roleService.deleteById(id);
            System.out.println("Deleted role: " + idStr);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid UUID format: " + idStr);
        }
    }
}