FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/ssafy-web-project-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} ssafy.jar
ENTRYPOINT ["java", "-jar", "/ssafy.jar"]