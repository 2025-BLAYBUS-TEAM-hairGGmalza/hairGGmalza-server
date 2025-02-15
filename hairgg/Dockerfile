# Builder 단계: Gradle 빌드를 실행
FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

# Gradle Wrapper 및 프로젝트 파일 복사
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./settings.gradle ./settings.gradle
COPY ./build.gradle ./build.gradle
COPY ./src ./src

# Gradle 빌드 실행 (테스트 제외)
RUN chmod +x ./gradlew && ./gradlew build -x test

# Runtime 단계: 빌드된 JAR 파일 실행
FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app

# 빌드된 JAR 파일을 빌드 단계에서 복사
COPY --from=builder /app/build/libs/hairgg-0.0.1-SNAPSHOT.jar /app/app.jar

# JAR 파일 실행
CMD ["java", "-jar", "app.jar"]
