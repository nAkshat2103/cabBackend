# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn .mvn
COPY mvnw mvnw.cmd pom.xml ./

# Copy the rest of the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Run the application (update jar name if needed)
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"] 