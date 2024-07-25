# Stage 1: Build Spring Boot App
FROM maven:latest as spring-builder

WORKDIR /app/backend

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY . .
RUN mvn package -DskipTests

# Stage 2: Serve Spring Boot App
FROM openjdk:latest

WORKDIR /app/backend

# Copy built Spring Boot JAR from previous stage
COPY --from=spring-builder /app/backend/target/*.jar /app/emailM.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "/app/emailM.jar"]
