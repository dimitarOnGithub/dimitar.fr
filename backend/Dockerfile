FROM eclipse-temurin:21
RUN useradd spring && usermod -a -G spring spring
USER spring:spring
COPY target/web-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]