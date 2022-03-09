REM build project
@echo off

echo "begin project build"
mvn package -DskipTests spring-boot:repackage
echo "end project build"
