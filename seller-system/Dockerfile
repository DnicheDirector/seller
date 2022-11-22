FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/seller-system.jar seller-system.jar
RUN apk add --no-cache netcat-openbsd
EXPOSE 8070