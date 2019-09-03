FROM openjdk:8
ADD target/app-app.jar app.war
EXPOSE 8085
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:h2:tcp://192.168.1.5/~/fotoshop_myapp1","-jar", "app.war"]