#!/bin/bash

docker rmi mroncatto/itflow-api
docker rmi mroncatto/itflow-api:1.0-SNAPSHOT
docker build -t mroncatto/itflow-api .
docker tag mroncatto/itflow-api mroncatto/itflow-api:1.0-SNAPSHOT
docker push mroncatto/itflow-api:1.0-SNAPSHOT