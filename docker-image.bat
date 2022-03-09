REM create docker images of buyer/seller services

@echo off

echo "begin docker image build"
echo "->> buyer-service"
docker build -t buyer-svc-dblocal .\eauction-buyer\.
echo 
echo "->> seller-service"
docker build -t seller-svc-dblocal .\eauction-seller\.
echo "end docker image build"
