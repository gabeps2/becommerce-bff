#BUILD STAGE
FROM maven:3.6.3-jdk-11-slim as build
RUN mkdir /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

#PACKAGE STAGE
FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
COPY --from=build /home/app/target/becommerce-1.0.0-SNAPSHOT.jar /usr/local/lib/becommerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/becommerce.jar"]
