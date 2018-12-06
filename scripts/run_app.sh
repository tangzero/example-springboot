#!/usr/bin/env bash

# compile
docker run --rm --net=todos -v $(pwd)/.:/opt/ -it -p 9090:8080 dockerdemo-base mvn -Dmaven.test.skip=true package
# run
docker run --rm --name=backend-java --net=todos --ip 172.18.0.21  -v $(pwd)/.:/opt/ -it -p 9090:8080 dockerdemo-base java -jar ./target/todo-0.0.1-SNAPSHOT.jar
