FROM adoptopenjdk/openjdk11:latest

COPY target/auto-app-0.0.1-SNAPSHOT.jar auto-app-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "auto-app-0.0.1-SNAPSHOT.jar", "Application"]