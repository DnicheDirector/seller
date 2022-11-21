FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/config-server.jar config-server.jar
ENTRYPOINT ["java", "-jar","config-server.jar"]
EXPOSE 8888