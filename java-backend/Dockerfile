FROM eclipse-temurin:17
MAINTAINER andre-goldmann.de
#RUN mkdir /opt/app

# Expose the port the app runs on
EXPOSE 4000

COPY target/spring-backend.jar spring-backend.jar
CMD ["java", "-jar", "/spring-backend.jar"]