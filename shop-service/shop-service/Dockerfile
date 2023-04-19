FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/shop-service-1.0.jar
COPY ${JAR_FILE} ShopService.jar
ENTRYPOINT ["java","-jar","ShopService.jar"]