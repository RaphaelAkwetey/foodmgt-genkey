# First stage: Build the JAR file using Maven
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory in the build container
WORKDIR /app

# Copy the Maven project files (pom.xml) and source code (src folder)
COPY pom.xml .
COPY src ./src

# Run Maven to build the project and generate the JAR file
RUN mvn clean package

# Second stage: Run the application using a smaller base image (Amazon Corretto)
FROM amazoncorretto:11

# Set the working directory in the second stage
WORKDIR /app

# Copy the built JAR file from the previous Maven build stage
COPY --from=build /app/target/foodmgt-0.0.1-SNAPSHOT.jar foodmgt.jar

# Expose the port that the app will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "foodmgt.jar"]
