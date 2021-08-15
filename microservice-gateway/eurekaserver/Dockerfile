FROM adoptopenjdk/openjdk11:ubi
COPY /target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]