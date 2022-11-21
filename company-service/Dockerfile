FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/company-service.jar company-service.jar
RUN apk add --no-cache netcat-openbsd
EXPOSE 8072