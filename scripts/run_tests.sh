#!/usr/bin/env bash
docker run --rm --net=todos -v $(pwd)/src/:/opt/src dockerdemo-base mvn test