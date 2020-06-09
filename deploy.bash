#!/bin/bash
# Create folder includes code
mkdir -p deploy/server &&
cp docker-compose.yml deploy/ &&
cd server &&
mvn clean install
echo "Step1: Finish create jar file"
mv target/*.jar target/app.jar &&
cd .. &&
mkdir deploy/server/target &&
cp -v server/target/app.jar deploy/server/target/ && cp -v server/application.properties deploy/server/target/
echo "Step2: Finish copy jar file to deploy folder"
cp server/Dockerfile deploy/server/ &&
mkdir -p deploy/client && rsync -avr --exclude='node_modules' client/ deploy/client/
echo "Step3: Finish make data local"
rsync -avzhe "ssh -i /mnt/ec2-sin.pem" deploy/ ec2-user@54.255.250.93:/home/ec2-user/
echo "Step4: Finish upload file to server"
ssh -i /mnt/ec2-sin.pem ec2-user@54.255.250.93 "cd /home/ec2-user && sudo docker-compose up --force-recreate --build -d&&sudo docker image prune -f"
#ssh -i /mnt/ec2-sin.pem ec2-user@54.255.250.93 "sudo docker rm $(docker ps -aq)"
echo "Step5: Connect ssh to server"

echo "Step6: Execute docker compose"

echo "Step6: Check result docker compose"
rm -rf deploy
echo "Step7: Exit connect to server and remove folder deploy"