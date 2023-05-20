FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/config-service-0.0.1jar ConfigService.jar
ENTRYPOINT ["java","-jar","ConfigService.jar"]