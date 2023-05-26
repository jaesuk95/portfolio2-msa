FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/order-service-0.0.8.jar OrderService.jar
ENTRYPOINT ["java","-jar","OrderService.jar"]