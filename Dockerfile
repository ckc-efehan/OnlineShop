# Stage 1: Build the application with Gradle
FROM gradle:8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Stage 2: Create the final image
FROM openjdk:21-slim
LABEL authors="efehan.cekic"
WORKDIR /app
COPY --from=builder /app/build/libs/OnlineShop-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]