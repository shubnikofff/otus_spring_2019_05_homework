FROM maven:3.6-jdk-8 AS compiler

ENV ARTIFACT_NAME=backend

WORKDIR /opt

COPY ./pom.xml .
RUN mvn dependency:resolve

COPY ./src ./src
RUN mvn package

FROM openjdk:8-alpine

WORKDIR /opt

COPY --from=compiler /opt/target/backend.jar /opt

EXPOSE 8080

CMD ["java", "-jar", "/opt/backend.jar"]
