# Web Services Architecture Laboratory

A microservices-based application with an Angular frontend for managing employee roles and employees.

## Architecture Overview

This project consists of:
- **Gateway** (port 8080) - Spring Cloud Gateway routing requests to services with CORS support
- **Role Service** (port 8081) - Manages employee roles
- **Employee Service** (port 8082) - Manages employees
- **Frontend** (port 4200) - Angular application for user interface

## Prerequisites

- Java 21
- Maven 3.9+
- Node.js 18+
- npm 9+

## Running the Backend

### Start all services:

```bash
# Start Role Service (port 8081)
cd role-service
./mvnw spring-boot:run

# Start Employee Service (port 8082)
cd employee-service
./mvnw spring-boot:run

# Start Gateway (port 8080)
cd gateway
./mvnw spring-boot:run
```

**Note:** The gateway includes CORS configuration to allow requests from `http://localhost:4200`. If you run the frontend on a different port/domain, update `gateway/src/main/resources/application.yml`.

## Running the Frontend

```bash
cd frontend
npm install
npm start
```

Navigate to `http://localhost:4200/`

### Configuring API Base URL

Edit `frontend/src/environments/environment.ts` to change the gateway URL:

```typescript
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080'  // Change this to your gateway URL
};
```

## API Endpoints

### Categories (Employee Roles)
- `GET /api/employee-roles` - List all roles
- `POST /api/employee-roles` - Create a role
- `GET /api/employee-roles/:id` - Get role by ID
- `PUT /api/employee-roles/:id` - Update role
- `DELETE /api/employee-roles/:id` - Delete role

### Elements (Employees)
- `GET /api/employees` - List all employees
- `POST /api/employees` - Create an employee
- `GET /api/employees/:id` - Get employee by ID
- `PUT /api/employees/:id` - Update employee
- `DELETE /api/employees/:id` - Delete employee
- `GET /api/employees/roles/:roleId` - List employees by role

## Project Structure

```
├── gateway/              # Spring Cloud Gateway
├── role-service/         # Employee Roles microservice
├── employee-service/     # Employees microservice
├── frontend/             # Angular frontend application
│   ├── src/app/
│   │   ├── components/   # Angular components
│   │   ├── services/     # Angular services
│   │   ├── models/       # TypeScript interfaces
│   │   └── environments/ # Environment configuration
│   └── README.md
└── README.md
```

## Frontend Features

- Categories list with CRUD operations
- Category details with associated elements
- Element management within categories
- Form validation
- Error handling with user-friendly alerts
