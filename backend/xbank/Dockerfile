FROM openjdk:11
ADD ./target/xbank-0.0.1.jar /tmp/xbank-0.0.1.jar
WORKDIR /tmp
ENTRYPOINT ["java","-jar", "xbank-0.0.1.jar"]