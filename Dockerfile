FROM maven:3.8.6-openjdk-8 AS backend-build
WORKDIR /app/backend
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN mvn dependency:go-offline -B
COPY src src
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-alpine

ENV TZ=America/Sao_Paulo
ENV JAVA_OPTS="-Xmx512m -Xms256m"

RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]