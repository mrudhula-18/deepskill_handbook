# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Leverage layer caching: copy pom.xml first and pre-fetch dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

COPY src ./src
RUN mvn -B -f pom.xml clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-alpine AS run
WORKDIR /app

COPY --from=build /build/target/bookstore-backend.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
