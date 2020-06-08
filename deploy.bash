#!/bin/bash
# Create folder includes code
mkdir -p deploy/server &&
cp docker-compose.yml deploy/ &&
cd server &&
mvn clean install;
echo "Finish create jar file"
mv target/*.jar target/app.jar &&
cd .. &&
mkdir deploy/server/target &&
cp -v server/target/app.jar deploy/server/target/;
echo "Finish copy jar file to deploy folder"
cp server/Dockerfile deploy/server/ &&
mkdir -p deploy/client && rsync -avr --exclude='node_modules' client/ deploy/client/