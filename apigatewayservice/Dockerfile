FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/apigatewayservice-0.0.8.jar Apigateway.jar
ENTRYPOINT ["java","-jar","Apigateway.jar"]