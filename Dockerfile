FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/wave-access.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]