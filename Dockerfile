FROM openjdk:18
EXPOSE 8081
ADD target/dev-automation.jar dev-automation.jar
ENTRYPOINT["java","-jar","/dev-automation.jar"]