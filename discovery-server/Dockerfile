FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/discovery-server.jar discovery-server.jar
ENTRYPOINT ["java", "-jar","discovery-server.jar"]
EXPOSE 8761