# 1. Maven 빌드 스테이지
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# 2. 실행용 JRE 이미지
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/target/*.jar app.jar

# unzip 설치 (런타임에 압축 해제용)
RUN apt-get update && apt-get install -y unzip

# entrypoint 스크립트 복사 및 실행 권한 부여
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]
