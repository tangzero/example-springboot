#!/bin/bash
mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml -Dmaven.test.skip=true install
mvn -Dmaven.test.skip=true package