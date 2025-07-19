# 1. Maven으로 빌드할 이미지
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# 2. 실행할 최소 JRE 이미지
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
