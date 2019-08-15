FROM maven:3-jdk-11-slim as javademo-artifact
WORKDIR /opt
COPY pom.xml ./
COPY ./src ./src
RUN mvn  -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml -Dmaven.test.skip=true package

FROM openjdk:11.0-jre-slim
COPY --from=javademo-artifact /opt/target/todo-0.0.1-SNAPSHOT.jar /opt/app.jar
COPY scripts/start_app.sh ./
ENV JAVA_OPTS -Xms256m -Xmx512m
RUN adduser --disabled-login javauser
USER javauser
WORKDIR /opt
CMD /bin/sh start_app.sh