WORKDIR /app

FROM amazoncorretto:11

COPY --from=build /target/app/foodmgt-0.0.1-SNAPSHOT.jar foodmgt.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "foodmgt.jar"]
