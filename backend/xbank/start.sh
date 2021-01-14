#!/bin/sh
cd keycloak-server && docker-compose up -d
cd ../ && mvn clean install -DskipTests=true
nohup java -jar ./target/xbank-0.0.1.jar > output.log &