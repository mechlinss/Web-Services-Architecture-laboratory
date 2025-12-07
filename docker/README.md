# Web Services Architecture - Laboratory 6
## Docker Containerization Setup

This repository contains a microservices architecture with Angular frontend and Spring Boot backend services, all containerized with Docker.

## Architecture Overview

The application consists of:
- **Frontend**: Angular application served by NGINX (port 4200)
- **Gateway**: Spring Cloud Gateway for API routing (port 8080)
- **Role Service**: Spring Boot REST API for managing employee roles (port 8081)
- **Employee Service**: Spring Boot REST API for managing employees (port 8082)
- **PostgreSQL Databases**: Two separate PostgreSQL instances for role and employee services

## Prerequisites

- Docker (version 20.10 or higher)
- Docker Compose (version 2.0 or higher)
- Maven (for building Spring Boot applications)
- Node.js 18+ and npm (for building Angular application)

## Quick Start

### Option 1: Build and Run Everything with Docker Compose

1. **Build the Spring Boot applications** (generate JAR files):
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Build and start all services**:
   ```bash
   docker-compose up --build
   ```

3. **Access the application**:
   - Frontend: http://localhost:4200
   - API Gateway: http://localhost:8080
   - Role Service: http://localhost:8081
   - Employee Service: http://localhost:8082

4. **Stop all services**:
   ```bash
   docker-compose down
   ```

5. **Stop and remove volumes** (removes database data):
   ```bash
   docker-compose down -v
   ```

### Option 2: Run Services Individually

You can also run specific services:

```bash
# Start only databases
docker-compose up postgres-role postgres-employee

# Start backend services
docker-compose up role-service employee-service gateway

# Start frontend
docker-compose up frontend
```

## Building the Applications

### Backend Services (Spring Boot)

Build all services at once:
```bash
./mvnw clean package -DskipTests
```

Or build individually:
```bash
cd role-service && mvn clean package -DskipTests
cd employee-service && mvn clean package -DskipTests
cd gateway && mvn clean package -DskipTests
```

### Frontend (Angular)

The frontend is built automatically during Docker image creation. If you want to build it manually:
```bash
cd frontend
npm install
npm run build
```

## Docker Configuration

### Environment Variables

#### Frontend
- `API_URL`: Backend gateway URL (default: http://gateway:8080)

#### Gateway
- `SERVER_PORT`: Gateway port (default: 8080)
- `ROLE_SERVICE_URL`: Role service URL (default: http://role-service:8081)
- `EMPLOYEE_SERVICE_URL`: Employee service URL (default: http://employee-service:8082)
- `ALLOWED_ORIGINS`: CORS allowed origins (default: http://localhost:4200)

#### Role Service
- `SERVER_PORT`: Service port (default: 8081)
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_DATASOURCE_DRIVER`: Database driver class
- `SPRING_JPA_DDL_AUTO`: Hibernate DDL mode (default: update)
- `SPRING_JPA_DATABASE_PLATFORM`: Hibernate dialect

#### Employee Service
- Same as Role Service, but with port 8082

### Database Configuration

Two PostgreSQL databases are configured:
- **postgres-role**: For role service (port 5433 on host)
- **postgres-employee**: For employee service (port 5434 on host)

Data is persisted in Docker volumes:
- `pgdata_role`
- `pgdata_employee`

## Troubleshooting

### Port Conflicts
If you have services running on ports 4200, 8080, 8081, 8082, 5433, or 5434, stop them or modify the port mappings in `docker-compose.yml`.

### Build Failures
Ensure all JAR files are built before running `docker-compose up --build`:
```bash
./mvnw clean package -DskipTests
```

### Database Connection Issues
If services can't connect to databases, ensure:
1. PostgreSQL containers are running: `docker-compose ps`
2. Check logs: `docker-compose logs postgres-role postgres-employee`

### Viewing Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f role-service
docker-compose logs -f employee-service
docker-compose logs -f gateway
docker-compose logs -f frontend
```

## Development

### Local Development without Docker
You can still run services locally:

1. Start backend services:
   ```bash
   cd role-service && mvn spring-boot:run
   cd employee-service && mvn spring-boot:run
   cd gateway && mvn spring-boot:run
   ```

2. Start frontend:
   ```bash
   cd frontend && npm start
   ```

### Switching Between H2 and PostgreSQL
By default, services use H2 in-memory database when run locally. When using Docker, they use PostgreSQL. This is controlled by environment variables in `docker-compose.yml`.

## Project Structure

```
.
├── docker/
│   ├── frontend/
│   │   ├── Dockerfile
│   │   ├── default.conf.template
│   │   └── entrypoint.sh
│   ├── gateway/
│   │   └── Dockerfile
│   ├── role-service/
│   │   └── Dockerfile
│   └── employee-service/
│       └── Dockerfile
├── docker-compose.yml
├── frontend/                 # Angular application
├── gateway/                  # Spring Cloud Gateway
├── role-service/            # Role management service
├── employee-service/        # Employee management service
└── README.md
```

## API Endpoints

### Role Service (via Gateway)
- `GET /api/employee-roles` - List all roles
- `GET /api/employee-roles/{id}` - Get role by ID
- `POST /api/employee-roles` - Create new role
- `PUT /api/employee-roles/{id}` - Update role
- `DELETE /api/employee-roles/{id}` - Delete role

### Employee Service (via Gateway)
- `GET /api/employees` - List all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create new employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

## License

This is a laboratory project for educational purposes.
