FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY finatiol-common/pom.xml ./finatiol-common/pom.xml
COPY finatiol-common/src ./finatiol-common/src
RUN mvn -f finatiol-common/pom.xml install -DskipTests -q
COPY finatiol-usuarios-ms/pom.xml ./finatiol-usuarios-ms/pom.xml
COPY finatiol-usuarios-ms/src ./finatiol-usuarios-ms/src
RUN mvn -f finatiol-usuarios-ms/pom.xml package -DskipTests -q

FROM eclipse-temurin:21-jre
WORKDIR /app
EXPOSE 8082
COPY --from=build /app/finatiol-usuarios-ms/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]