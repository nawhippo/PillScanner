# Use Maven base image to build the project
FROM maven:3.8.4-openjdk-17 as builder
# Create a directory for your application
WORKDIR /app
# Copy the source code and pom.xml to the /app directory in the container
COPY src /app/src
COPY pom.xml /app
# Build the application
RUN mvn -f /app/pom.xml clean package

# Use Eclipse Temurin base image for running the application
FROM eclipse-temurin:17-jre
# Copy the built jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Set the entry point for the container
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar /app.jar"]