FROM openjdk:17-jdk

ARG JAR_FILE="build/libs/eureka-server-0.0.1-SNAPSHOT.jar"

COPY ${JAR_FILE} app.jar

ENV    PROFILE dev

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar","/app.jar"]