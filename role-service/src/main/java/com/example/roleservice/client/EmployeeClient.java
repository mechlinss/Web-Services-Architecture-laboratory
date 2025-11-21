package com.example.roleservice.client;

import com.example.roleservice.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class EmployeeClient {

    private final RestTemplate restTemplate;
    private final String employeeServiceUrl;

    public EmployeeClient(RestTemplate restTemplate,
                      @Value("${employee.service.url:http://localhost:8082}") String employeeServiceUrl) {
        this.restTemplate = restTemplate;
        this.employeeServiceUrl = employeeServiceUrl;
    }

    public List<EmployeeDto> getEmployeesByEmployeeRoleId(UUID employeeRoleId) {
        try {
            String url = employeeServiceUrl + "/api/employees/roles/" + employeeRoleId;
            ResponseEntity<List<EmployeeDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EmployeeDto>>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Failed to fetch employees from employee service: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void deleteEmployeesByEmployeeRoleId(UUID employeeRoleId) {
        try {
            String url = employeeServiceUrl + "/api/employees/roles/" + employeeRoleId;
            restTemplate.delete(url);
            System.out.println("Deleted songs for role: " + employeeRoleId);
        } catch (Exception e) {
            System.err.println("Failed to delete employees from employee service: " + e.getMessage());
            throw new RuntimeException("Failed to delete songs for album " + employeeRoleId, e);
        }
    }

    public void updateEmployeesRole(UUID roleId, String roleName) {
        try {
            String url = employeeServiceUrl + "/api/employees/roles/" + roleId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(roleName, headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    request,
                    Void.class
            );

            System.out.println("Updated employees for role: " + roleId);
        } catch (Exception e) {
            System.err.println("Failed to update employees: " + e.getMessage());
            throw new RuntimeException("Failed to update employees for role " + roleId, e);
        }
    }
}