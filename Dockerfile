FROM openjdk:17-jdk-slim AS build

WORKDIR /build

RUN mkdir -p /root/.m2 \
      && mkdir /root/.m2/repository

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

ENV ARTIFACT_ID=djidya-java-kubernetes-template
ENV VERSION=0.0.1-SNAPSHOT

COPY --from=build /build/target/${ARTIFACT_ID}-${VERSION}.jar /app/target/${ARTIFACT_ID}-${VERSION}.jar

ENTRYPOINT java -jar /app/target/${ARTIFACT_ID}-${VERSION}.jar