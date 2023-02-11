#FROM eclipse-temurin:17-jdk-alpine as build
#COPY . .
#RUN chmod +x gradlew && ./gradlew build

FROM eclipse-temurin:17-jdk-alpine
EXPOSE 5000
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
