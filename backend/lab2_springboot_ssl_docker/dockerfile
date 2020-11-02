FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=./target/helloworld-0.0.1.jar
COPY ${JAR_FILE} spring-boot-ssl.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-ssl.jar"]