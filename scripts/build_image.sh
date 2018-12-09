#!/usr/bin/env bash
mayor_version="1.0"
minor_version=$BUILD_NUMBER
version="$mayor_version.$minor_version"
docker build --rm -t herreraluis/javademo:$version -f Dockerfile .