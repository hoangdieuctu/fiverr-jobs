FROM openjdk:8-alpine
COPY /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]