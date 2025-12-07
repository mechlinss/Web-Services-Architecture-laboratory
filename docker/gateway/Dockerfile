# Use Eclipse Temurin JDK 17
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR file
COPY gateway/target/*.jar /app/service.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
