FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/recommend-service-1.0.jar
COPY ${JAR_FILE} RecommendService.jar
ENTRYPOINT ["java","-jar","RecommendService.jar"]