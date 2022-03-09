REM stop containers & remove images

@echo off

echo "stop app service containers"
echo "->> buyer-service"
docker stop buyer-eauc-dblocal
echo 
echo "->> seller-service"
docker stop seller-eauc-dblocal

echo "removing containers"
docker rm buyer-eauc-dblocal
docker rm seller-eauc-dblocal

echo
echo "removing images"
docker rmi buyer-svc-dblocal
docker rmi seller-svc-dblocal
