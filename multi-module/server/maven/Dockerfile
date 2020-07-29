FROM openjdk:8-jdk

ARG MAVEN_VERSION=3.0.5
ARG USER_HOME_DIR="/root"
ARG SHA=748c7deb8f56dd968a4dbc289b012adea3621f7b3847dc19004995c2f540bae68fb100c0f28caa57b0f9d15b74ae81eaa27c71e342342aa57afe23b41e80d35f
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
ARG JAR_FILE=target/*.jar

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings-docker.xml /usr/share/maven/ref/

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

