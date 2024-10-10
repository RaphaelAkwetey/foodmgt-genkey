#!/bin/bash
set -eu
restore(){
  echo "Deployment was not successful, reverting to previous deployment";
  rm -rf /foodmgt/*.jar;
  cd /foodmgt/backup;
  cp "$(ls -t /foodmgt/backup | head -1)" /foodmgt/;
  mv /foodmgt/food-* /foodmgt/foodmgt-0.0.1-SNAPSHOT.jar;
  docker-compose stop foodmgt;
  docker-compose build --no-cache foodmgt;
  docker-compose up foodmgt;
  echo "done";
  exit 0;
}
sleep 120s && curl --fail  --insecure https://localhost:8086/login
res=$?

if (curl -v --insecure https://localhost:8086/login)
then
  echo "Applications have succesfuly been deployed!!"
  exit 0
elif test "$res" != "0"
then
  restore

else
  echo "Deployment failed!";
  exit 1;

fi

