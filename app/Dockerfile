FROM openjdk:8
ADD /target/app-app.jar app.war
EXPOSE 8085
ENTRYPOINT ["java","-jar", "app.war"]
