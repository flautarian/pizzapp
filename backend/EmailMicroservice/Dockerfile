# Stage 1: Build Spring Boot App
FROM maven:latest as spring-builder

# Set the working directory for the build context
WORKDIR /app/backend

# Copy the entire project into the build context
COPY ../ .

# Build only the OrderMicroservice module and package it as an executable JAR
RUN mvn clean package -pl EmailMicroservice -am

# Stage 2: Serve Spring Boot App
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the final image
WORKDIR /app

# Copy built Spring Boot JAR from previous stage
COPY --from=spring-builder /app/backend/EmailMicroservice/target/emailmicroservice-SHADED.jar /app/emailMicroservice.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "/app/emailMicroservice.jar"]
