FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/product-service-0.0.2.jar ProductService.jar
ENTRYPOINT ["java","-jar","ProductService.jar"]