FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/cctransproc-0.0.1-SNAPSHOT.jar /app/cctransproc-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "cctransproc-0.0.1-SNAPSHOT.jar"]