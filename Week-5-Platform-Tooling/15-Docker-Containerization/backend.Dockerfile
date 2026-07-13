# backend.Dockerfile - multi-stage build for a Spring Boot backend
#
# Build command (run from the repo root, adjusting the context path to the
# actual Spring Boot module you want to containerize - e.g. the Week-3
# Module-9 Spring REST app or the Week-7 capstone backend):
#
#   docker build -f backend.Dockerfile -t dn5-backend:latest ../../Week-3-Data-and-REST/09-Spring-REST-SpringBoot3
#
# or, for the Week-7 capstone backend:
#
#   docker build -f backend.Dockerfile -t dn5-backend:latest ../../Week-7-Capstone/backend
#
# The build context (the path argument) must contain pom.xml and src/ at
# its root for the COPY instructions below to resolve correctly.

# ---------------------------------------------------------------------------
# Stage 1: build the application with Maven
# ---------------------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

# Copy just the pom first so dependency resolution is cached across builds
# when only source files change.
COPY pom.xml .
RUN mvn -B dependency:go-offline || true

COPY src ./src

RUN mvn -B clean package -DskipTests

# ---------------------------------------------------------------------------
# Stage 2: run the application on a slim JRE
# ---------------------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage. Adjust the wildcard if your
# module produces a differently-named artifact (e.g. app-0.0.1-SNAPSHOT.jar).
COPY --from=build /build/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
