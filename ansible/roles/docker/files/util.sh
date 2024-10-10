#!/bin/bash
set -eu

#Backup jar file with current date
mkdir -p backup
now=$(date +"%m_%d_%Y_%M")
FILE=/foodmgt/foodmgt-0.0.1-SNAPSHOT.jar
if test -f "$FILE"; then
    echo "$FILE exists."
    cd /foodmgt/
    mv foodmgt-0.0.1-SNAPSHOT.jar foodmgt-0.0.1-SNAPSHOT.jar-${now}
    mv foodmgt-0.0.1-SNAPSHOT.jar-${now} /foodmgt/backup
    echo "Done backing up!!"

else
  echo "The deployment jar doesn't exist!!!"
fi