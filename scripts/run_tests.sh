#!/usr/bin/env bash
docker run --rm --net=todos -v $(pwd)/src/:/opt/src herreraluis/javademo-artifact mvn test