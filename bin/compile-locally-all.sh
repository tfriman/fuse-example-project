#! /bin/sh

# compiles all
args="clean install -DskipTests"
mvn -f artifacts/pom.xml $args
mvn -f inbound/pom.xml $args
mvn -f integrationtestserver/pom.xml $args
mvn -f outbound/pom.xml $args
mvn -f xlate/pom.xml $args
