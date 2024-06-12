FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Fitlux_Backend-0.0.1-SNAPSHOT.jar Fitlux_Backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Fitlux_Backend.jar"]