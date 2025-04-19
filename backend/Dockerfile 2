# 빌드 스테이지
FROM openjdk:17-slim AS build

WORKDIR /app

COPY gradlew gradle build.gradle settings.gradle ./
COPY gradle/wrapper gradle/wrapper
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon

# 실행 스테이지 (JAR 실행)
FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
