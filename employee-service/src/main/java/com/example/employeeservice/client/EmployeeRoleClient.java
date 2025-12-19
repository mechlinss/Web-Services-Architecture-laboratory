package com.example.employeeservice.client;

import com.example.employeeservice.dto.EmployeeListDto;
import com.example.employeeservice.dto.EmployeeRoleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class EmployeeRoleClient {

    private final RestTemplate restTemplate;
    private final String employeeRoleServiceUrl;

    public EmployeeRoleClient(RestTemplate restTemplate,
                       @Value("${ROLE_SERVICE_URL:http://localhost:8081}") String employeeRoleServiceUrl) {
        this.restTemplate = restTemplate;
        this.employeeRoleServiceUrl = employeeRoleServiceUrl;
    }

    public EmployeeRoleDto getEmployeeRoleById(UUID employeeRoleId) {
        try {
            String url = employeeRoleServiceUrl + "/api/employee-roles/" + employeeRoleId;
            return restTemplate.getForObject(url, EmployeeRoleDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employee role from role service", e);
        }
    }

    public List<EmployeeRoleDto> getAllEmployeeRoles() {
        try {
            String url = employeeRoleServiceUrl + "/api/employee-roles";
            ResponseEntity<List<EmployeeRoleDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EmployeeRoleDto>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employee roles from roles service", e);
        }
    }
}
