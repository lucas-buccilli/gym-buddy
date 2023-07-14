FROM amazoncorretto:17

VOLUME /tmp

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app.jar"]