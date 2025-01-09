FROM openjdk:21-jdk-slim

COPY build/libs/*.jar ./out.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./out.jar"]