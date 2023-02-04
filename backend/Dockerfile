FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=build/libs/ssafy-web-project-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} ssafy.jar
ENTRYPOINT ["java", "-jar", "/ssafy.jar"]
