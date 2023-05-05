FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/reservation-service-1.0.jar
COPY ${JAR_FILE} ReservationService.jar
ENTRYPOINT ["java","-jar","ReservationService.jar"]