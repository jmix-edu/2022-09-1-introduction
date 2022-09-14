FROM bellsoft/liberica-openjdk-alpine
WORKDIR opt
COPY task-management-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/opt/app.jar"]