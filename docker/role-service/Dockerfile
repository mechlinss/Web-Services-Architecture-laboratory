# Use Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the JAR file
COPY role-service/target/*.jar /app/service.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
