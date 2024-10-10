FROM openjdk:11
LABEL maintainer="SJA"
WORKDIR /app
COPY . .
RUN chmod 777 -R /app/config
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "foodmgt-0.0.1-SNAPSHOT.jar"]