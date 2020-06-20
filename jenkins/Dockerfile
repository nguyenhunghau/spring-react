FROM jenkins/jenkins

USER root
RUN apt-get update && apt-get install -y maven
RUN apt-get install -y curl \
  && curl -sL https://deb.nodesource.com/setup_9.x | bash - \
  && apt-get install -y nodejs \
  && curl -L https://www.npmjs.com/install.sh | sh
# drop back to the regular jenkins user - good practice
# USER jenkins