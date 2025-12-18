# Stage 1: Build with Maven
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /workspace

# Copy Maven wrapper and pom files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY role-service/pom.xml role-service/

# Download dependencies
RUN ./mvnw dependency:go-offline -pl role-service -am

# Copy source code
COPY role-service/src role-service/src

# Build the application
RUN ./mvnw clean package -pl role-service -am -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /workspace/role-service/target/*.jar /app/service.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
