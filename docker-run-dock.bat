REM start containers - using buyer/seller images

@echo off

echo "start app service containers"
echo "->> buyer-service"
docker run -d -p 8082:8082 --name buyer-eauc-dock --network=eauc buyer-svc-dbdock

echo 

echo "->> seller-service"
docker run -d -p 8081:8081 --name seller-eauc-dock --network=eauc seller-svc-dbdock
