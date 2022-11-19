#BUILD STAGE
FROM maven:3.6.3-jdk-11-slim as build
RUN mkdir /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

#PACKAGE STAGE
FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim

# environment variable with default value
ENV JDBC_URL=jdbc:postgresql://127.0.0.1:5432/BeCommerce
ENV JDBC_DRIVER=org.postgresql.Driver
ENV JDBC_USER=root
ENV JDBC_PASSWORD=root

COPY --from=build /home/app/target/becommerce-1.0.0-SNAPSHOT.jar /usr/local/lib/becommerce.jar
EXPOSE 8080
ENTRYPOINT java -DJDBC_URL=${JDBC_URL} -DJDBC_DRIVER=${JDBC_DRIVER} -DJDBC_USER=${JDBC_USER} -DJDBC_PASSWORD=${JDBC_PASSWORD} -jar /usr/local/lib/becommerce.jar
