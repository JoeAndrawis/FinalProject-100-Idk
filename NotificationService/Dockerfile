# Use a base image with JDK
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy JAR file
COPY target/NotificationService-0.0.1-SNAPSHOT.jar app.jar

# Add health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8083/actuator/health || exit 1

# Set Java options and memory limits
ENV JAVA_OPTS="-Xmx512m -Djava.security.egd=file:/dev/./urandom"

# Expose the correct port
EXPOSE 8083

# Run the application with Java options
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
