FROM adoptopenjdk/openjdk11:ubi
COPY /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]