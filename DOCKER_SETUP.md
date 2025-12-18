# Docker Setup Guide - Quick Reference

## Prerequisites
- Docker and Docker Compose installed

## Quick Start

### 1. Start All Services
```bash
docker-compose up --build
```

The Spring Boot applications will be built automatically inside the Docker containers using multi-stage builds.

### 2. Access the Application
- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:8080
- **Role Service**: http://localhost:8081
- **Employee Service**: http://localhost:8082
- **PostgreSQL (Role)**: localhost:5433
- **PostgreSQL (Employee)**: localhost:5434

### 3. Stop Services
```bash
docker-compose down
```

## What Was Implemented

### Docker Infrastructure
1. **Frontend Container** (Angular + NGINX)
   - Multi-stage build with Node.js 18 and NGINX Alpine
   - Dynamic API URL configuration via environment variables
   - Located at: `docker/frontend/`

2. **Backend Containers** (Spring Boot Services)
   - Gateway service (port 8080)
   - Role service (port 8081)
   - Employee service (port 8082)
   - All using multi-stage builds with Eclipse Temurin 17 JDK Alpine
   - Maven builds happen inside Docker containers
   - Located at: `docker/gateway/`, `docker/role-service/`, `docker/employee-service/`

3. **Database Containers** (PostgreSQL)
   - Separate PostgreSQL 15 Alpine instances for each service
   - Persistent volumes for data storage
   - Services: `postgres-role` (5433) and `postgres-employee` (5434)

### Configuration Changes
1. **Spring Boot Services**
   - Added PostgreSQL driver dependencies
   - Updated `application.properties` with environment variable support
   - Consistent Spring Boot version (3.5.6) across all services
   - Added test dependencies

2. **Gateway**
   - Environment variables for service URLs and CORS
   - Fixed API routing paths to match actual controllers

3. **NGINX**
   - Dynamic configuration with `envsubst`
   - API proxy to gateway service

### Files Added
- `docker-compose.yml` - Complete orchestration
- `docker/*/Dockerfile` - Container definitions
- `docker/frontend/default.conf.template` - NGINX config template
- `docker/frontend/entrypoint.sh` - Dynamic configuration script
- `docker/README.md` - Comprehensive documentation
- `.dockerignore` - Build optimization

## Environment Variables

### Frontend
- `API_URL`: Backend gateway URL (default: http://gateway:8080)

### Gateway
- `SERVER_PORT`: Port (default: 8080)
- `ROLE_SERVICE_URL`: Role service URL (default: http://role-service:8081)
- `EMPLOYEE_SERVICE_URL`: Employee service URL (default: http://employee-service:8082)
- `ALLOWED_ORIGINS`: CORS origins (default: http://localhost:4200)

### Backend Services (Role & Employee)
- `SERVER_PORT`: Service port
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_DATASOURCE_DRIVER`: Database driver class
- `SPRING_JPA_DDL_AUTO`: Hibernate DDL mode (default: update)
- `SPRING_JPA_DATABASE_PLATFORM`: Hibernate dialect

## Network Architecture

```
Frontend (4200:80)
    ↓
Gateway (8080:8080)
    ↓
    ├── Role Service (8081:8081) ──→ PostgreSQL Role (5433:5432)
    └── Employee Service (8082:8082) ──→ PostgreSQL Employee (5434:5432)
```

## Troubleshooting

### Build Failures
If Docker build fails, check the logs:
```bash
docker-compose logs [service-name]
```

The builds happen inside Docker containers, so no local Maven installation is needed.

### Port Conflicts
Check if ports 4200, 8080-8082, 5433-5434 are available.

### Container Logs
```bash
docker-compose logs -f [service-name]
```

### Database Connection Issues
Verify PostgreSQL containers are running:
```bash
docker-compose ps
```

## Security Notes
- Default database credentials are provided for development
- Change credentials in production environments
- PostgreSQL services are accessible from host for debugging
- Data persists in Docker volumes between restarts

## Next Steps
1. Customize environment variables in `docker-compose.yml`
2. Update database credentials for production
3. Configure production NGINX settings
4. Set up CI/CD pipeline for automated builds
5. Add health checks to containers
6. Configure resource limits

## Cleanup
Remove all containers, networks, and volumes:
```bash
docker-compose down -v
```

## Support
For detailed information, see `docker/README.md`
