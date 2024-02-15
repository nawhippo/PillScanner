FROM maven:3.8.4-openjdk-17 as builder
COPY src/main/java/Nate/PillScanner .
RUN mvn -e -X package


FROM eclipse-temurin:17-jre
COPY --from=builder /target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar /app.jar"]