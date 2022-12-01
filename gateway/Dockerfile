FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/gateway.jar gateway.jar
RUN apk add --no-cache netcat-openbsd
EXPOSE 8080