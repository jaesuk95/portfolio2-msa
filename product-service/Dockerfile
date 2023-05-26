FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/product-service-0.0.4.jar ProductService.jar
ENTRYPOINT ["java","-jar","ProductService.jar"]