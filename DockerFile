# FROM maven:3.8.5-openjdk-17 as build
# COPY . .
# RUN mvn clean package -DskipTests
#
# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/qldt-0.0.1-SNAPSHOT.jar qldt.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","qldt.jar"]

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080