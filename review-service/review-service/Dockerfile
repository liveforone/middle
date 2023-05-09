FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/review-service-1.0.jar
COPY ${JAR_FILE} ReviewService.jar
ENTRYPOINT ["java","-jar","ReviewService.jar"]