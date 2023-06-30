FROM openjdk:8-jdk-alpine
WORKDIR /application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} newsportal.jar
ENTRYPOINT ["java","-jar","/application/newsportal.jar"]