#!/usr/bin/env bash

docker run --rm --name=backend-java \
        --net=todos --ip 172.18.0.21 -it \
        -p 9090:8080 dockerdemo
