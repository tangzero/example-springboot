#!/usr/bin/env bash
docker run -p 99:80 --name=pgadmin \
            --network todos \
            -e  PGADMIN_DEFAULT_EMAIL=user@domain.com \
            -e PGADMIN_DEFAULT_PASSWORD=supersecret \
            -d dpage/pgadmin4

docker run --network todos --name demodb \
           --ip 172.18.0.20 \
           -p 5432:5432 \
           -e POSTGRES_PASSWORD=the-secret \
           -e POSTGRES_USER=demo \
           -e POSTGRES_DB=datadb \
           -d postgres
