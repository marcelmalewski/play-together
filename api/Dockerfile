FROM openjdk:17 as main
WORKDIR /app
EXPOSE 5000
COPY --from=build /build/libs/fat.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]
