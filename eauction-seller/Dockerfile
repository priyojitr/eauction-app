# deploy - seller service
FROM java:8-jre
EXPOSE 8081
ADD ./target/eauction-seller-1.0.0.jar /usr/eauction-app/package/eauction-seller-1.0.0.jar
WORKDIR /usr/eauction-app/package
ENTRYPOINT ["java","-jar","eauction-seller-1.0.0.jar"]
