# ----------- Stage 1: Build -----------
FROM maven:3.8.5-openjdk-17-slim AS builder

# Set working directory inside container
WORKDIR /app

# Copy all files from project to working directory
COPY . .

# Run Maven to package the application (skip tests to save time)
RUN mvn clean package -DskipTests

# ----------- Stage 2: Runtime -----------
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
