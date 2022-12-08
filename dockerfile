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
ENV S3_ACCESS_KEY=s3_access_key
ENV S3_SECRET_KEY=s3_secret_key
ENV S3_BUCKET_NAME=s3_bucket_name
ENV S3_REGION=s3_region

COPY --from=build /home/app/target/becommerce-1.0.0-SNAPSHOT.jar /usr/local/lib/becommerce.jar
EXPOSE 8080
ENTRYPOINT java -DJDBC_URL=${JDBC_URL} -DJDBC_DRIVER=${JDBC_DRIVER} -DJDBC_USER=${JDBC_USER} -DJDBC_PASSWORD=${JDBC_PASSWORD} -DJWT_SECRET_KEY=${JWT_SECRET_KEY} -DJWT_EXPIRATION_TIME=${JWT_EXPIRATION_TIME} -DS3_ACCESS_KEY=${S3_ACCESS_KEY} -DS3_SECRET_KEY=${S3_SECRET_KEY} -DS3_BUCKET_NAME=${S3_BUCKET_NAME} -DS3_REGION=${S3_REGION} -jar /usr/local/lib/becommerce.jar
