# Use a base image with JDK
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy JAR file
COPY target/NotificationService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
