FROM amazoncorretto:11

# Set the working directory in the second stage
VOLUME /tmp

# Copy the built JAR file from the previous Maven build stage
COPY target/foodmgt-0.0.1-SNAPSHOT.jar foodmgt.jar

# Expose the port that the app will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/foodmgt.jar"]
