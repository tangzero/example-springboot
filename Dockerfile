FROM openjdk:8-jre-alpine
COPY --from=herreraluis/javademo-artifact /opt/target/todo-0.0.1-SNAPSHOT.jar /opt/app.jar
COPY --from=herreraluis/javademo-artifact /opt/start_app.sh /opt/start_app.sh
ENV JAVA_OPTS -Xms256m -Xmx512m
RUN adduser -D javauser
USER javauser
WORKDIR /opt
CMD /bin/sh start_app.sh