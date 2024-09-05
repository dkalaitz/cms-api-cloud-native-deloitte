FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/cms_api-0.0.1-SNAPSHOT.jar cms-api.jar
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "cms-api.jar"]
