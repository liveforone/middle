FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/timetable-service-1.0.jar
COPY ${JAR_FILE} TimetableService.jar
ENTRYPOINT ["java","-jar","TimetableService.jar"]