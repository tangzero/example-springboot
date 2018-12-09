#!/usr/bin/env bash

state_container=$(docker inspect -f {{.State.Running}} backend-java)
if $state_container;
then
        docker rm -f backend-java
fi

docker run -d --name=backend-java \
        --net=todos --ip 172.18.0.21 \
        --restart=always \
        -p 9090:8080 herreraluis/javademo 
