FROM openjdk:17-jdk-slim AS build

WORKDIR /build

RUN mkdir -p /root/.m2 \
      && mkdir /root/.m2/repository

ARG SETTINGS_XML=settings.xml

COPY ${SETTINGS_XML} /root/.m2
COPY .mvn .mvn
COPY --chmod=777 mvnw .
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim

ENV ARTIFACT_ID=djidya-java-kubernetes-template
ENV VERSION=1.0

COPY --from=build /build/target/${ARTIFACT_ID}-${VERSION}.jar /app/target/${ARTIFACT_ID}-${VERSION}.jar

ENTRYPOINT java -jar /app/target/${ARTIFACT_ID}-${VERSION}.jar
