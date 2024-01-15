FROM maven:3.9.4-amazoncorretto-17 AS builder
COPY . /src
WORKDIR /src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /src/target/*.jar woyfit.jar
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "woyfit.jar"]
