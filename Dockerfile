FROM openjdk:24-ea-jdk-slim
WORKDIR /app
COPY /target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
