FROM openjdk:16
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} lwmk.jar
ENTRYPOINT ["java","-jar","/lwmk.jar"]