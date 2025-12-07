# Web Services Architecture Laboratory

Microservices architecture project with Angular frontend and Spring Boot backend services.

## 🚀 Quick Start with Docker

The easiest way to run the entire application is using Docker Compose:

```bash
# Build Spring Boot applications
./mvnw clean package -DskipTests

# Start all services
docker-compose up --build
```

Access the application at **http://localhost:4200**

For detailed Docker setup instructions, see [DOCKER_SETUP.md](DOCKER_SETUP.md) or [docker/README.md](docker/README.md).

## 📦 Architecture

The project consists of:

- **Frontend**: Angular 18 application
- **Gateway**: Spring Cloud Gateway for API routing
- **Role Service**: Spring Boot REST API for employee roles management
- **Employee Service**: Spring Boot REST API for employees management
- **Databases**: PostgreSQL instances for each service

## 🛠️ Development Setup

### Prerequisites

- Java 17
- Node.js 18+
- Maven 3.6+
- Docker and Docker Compose (for containerized deployment)

### Running Services Locally

#### Backend Services

```bash
# Build all services
./mvnw clean install

# Run gateway
cd gateway && mvn spring-boot:run

# Run role-service
cd role-service && mvn spring-boot:run

# Run employee-service
cd employee-service && mvn spring-boot:run
```

#### Frontend

```bash
cd frontend
npm install
npm start
```

Access at http://localhost:4200

## 🐳 Docker Deployment

See [DOCKER_SETUP.md](DOCKER_SETUP.md) for comprehensive Docker deployment guide.

### Services and Ports

| Service | Port | Description |
|---------|------|-------------|
| Frontend | 4200 | Angular application (NGINX) |
| Gateway | 8080 | API Gateway |
| Role Service | 8081 | Employee roles REST API |
| Employee Service | 8082 | Employees REST API |
| PostgreSQL (Role) | 5433 | Role service database |
| PostgreSQL (Employee) | 5434 | Employee service database |

## 📝 API Endpoints

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

## 🏗️ Project Structure

```
.
├── frontend/                 # Angular application
├── gateway/                  # Spring Cloud Gateway
├── role-service/            # Role management service
├── employee-service/        # Employee management service
├── docker/                  # Docker configurations
│   ├── frontend/
│   ├── gateway/
│   ├── role-service/
│   └── employee-service/
├── docker-compose.yml       # Complete orchestration
└── DOCKER_SETUP.md         # Docker guide
```

## 🔧 Configuration

### Environment Variables

All services support configuration via environment variables:

- Database connection settings
- Service ports
- API URLs
- CORS settings

See [DOCKER_SETUP.md](DOCKER_SETUP.md) for complete configuration reference.

## 📚 Documentation

- [Docker Setup Guide](DOCKER_SETUP.md) - Quick reference for Docker deployment
- [Detailed Docker Documentation](docker/README.md) - Comprehensive Docker guide

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run specific service tests
cd role-service && mvn test
```

## 📄 License

This is a laboratory project for educational purposes.
