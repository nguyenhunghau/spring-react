pipeline {
  agent any
  stages {
    stage('begin') {
      steps {
        sh '''#!/bin/bash
# Create folder includes code and build file jar
mkdir -p deploy/server && \\
cp docker-compose.yml deploy/ && \\
cd server && \\
mvn clean install
echo "Step1: Finish create jar file"

#Copy jar file, Dockerfile and docker-compose into folder deploy
mv target/*.jar target/app.jar &&
cd .. &&
mkdir deploy/server/target &&
cp -v server/target/app.jar deploy/server/target/ && cp -v application.properties deploy/server/target/ && cp -v server/Dockerfile deploy/server/
echo "Step2: Finish copy jar file to deploy folder"

#Create folder for deploy client and build project
mkdir -p deploy/client && cd client && npm install && \\
npm run build && cd .. && cp -R client/build deploy/client && cp -v client/Dockerfile deploy/client
echo "Step3: Finish make data local"

#Deploy code to server
(rsync -avzhe "ssh -i /mnt/ec2-sin.pem" deploy/ ec2-user@54.255.250.93:/home/ec2-user/) || exit 1
echo "Step4: Finish upload file to server"

#Connect to server using ssh and start docker-compose
ssh -i /mnt/ec2-sin.pem ec2-user@54.255.250.93 "cd /home/ec2-user && sudo docker-compose up --force-recreate --build -d&&sudo docker image prune -f"
#ssh -i /mnt/ec2-sin.pem ec2-user@54.255.250.93 "sudo docker rm $(docker ps -aq)"
echo "Step5: Connect ssh to server"

echo "Step6: Execute docker compose"

echo "Step6: Check result docker compose"
rm -rf deploy
echo "Step7: Exit connect to server and remove folder deploy"'''
      }
    }

  }
}
