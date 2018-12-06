#!/usr/bin/env bash
docker run -p 99:80 --name=pgadmin \
            --network todos \
            -e  PGADMIN_DEFAULT_EMAIL=user@domain.com \
            -e PGADMIN_DEFAULT_PASSWORD=supersecret \
            -d dpage/pgadmin4

docker run --network todos --name demodb \
           --ip 172.18.0.20 \
           -e POSTGRES_PASSWORD=supersecret \
           -e POSTGRES_USER=demo \
           -e POSTGRES_DB=demo_db \
           -d postgres
