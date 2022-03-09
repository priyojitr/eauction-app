REM create docker images of buyer/seller services

@echo off

echo "remove existing images"
docker rmi buyer-svc-dbdock
docker rmi seller-svc-dbdock

echo "begin docker image build"
echo "->> buyer-service"
docker build -t buyer-svc-dbdock .\eauction-buyer\.
echo 
echo "->> seller-service"
docker build -t seller-svc-dbdock .\eauction-seller\.
echo "end docker image build"
