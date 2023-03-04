FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /build

RUN mkdir -p /root/.m2 \
      && mkdir /root/.m2/repository

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:17-jdk

ENV ARTIFACT_ID=djidya-java-kubernetes-template
ENV VERSION=0.0.1-SNAPSHOT

COPY --from=build /build/target/${ARTIFACT_ID}-${VERSION}.jar /app/target/${ARTIFACT_ID}-${VERSION}.jar

ENTRYPOINT java -jar /app/target/${ARTIFACT_ID}-${VERSION}.jar