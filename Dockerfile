FROM quay.io/quarkus/centos-quarkus-maven:21.0.0-java11

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN mvn package -Dquarkus.package.type=uber-jar

CMD ["java", "-jar", "/usr/src/app/target/quarkus-app/quarkus-run.jar"]
