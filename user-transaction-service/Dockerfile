FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/user-transaction-service.jar user-transaction-service.jar
RUN apk add --no-cache netcat-openbsd
EXPOSE 8074