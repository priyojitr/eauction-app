# deploy - buyer service
FROM java:8-jre
EXPOSE 8082
ADD ./target/eauction-buyer-1.0.0.jar /usr/eauction-app/package/eauction-buyer-1.0.0.jar
WORKDIR /usr/eauction-app/package
ENTRYPOINT ["java","-jar","eauction-buyer-1.0.0.jar"]
