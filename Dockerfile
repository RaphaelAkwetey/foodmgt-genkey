FROM maven:3.8.6-openjdk-11 AS build

WORKDIR /app

COPY ./target/foodmgt-0.0.1-SNAPSHOT.jar ./

FROM amazoncorretto:11

WORKDIR /app

COPY --from=build /app/foodmgt-0.0.1-SNAPSHOT.jar foodmgt.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "foodmgt.jar"]
