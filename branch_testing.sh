#!/bin/bash
set -e

if [[ $BRANCH_NAME == *"testing"* ]];
then
   echo "************Running Ansible Playbook*************";\
   cd ./ansible && ansible-playbook main.yml --tags "general,test,compose" -vvv --extra-vars "target=test";

   if [ $? -eq 0 ];
   then
     echo "Ansible playbook was successful was successful!!"
     exit 0
   else
     echo "Failed to complete playbook!!"
     exit 1
   fi
fi

if [[ $BRANCH_NAME == *"feature/manual-entry"* ]];
then
   echo "************Running Ansible Playbook*************";\
   cd ./ansible && ansible-playbook main.yml --tags "general,test,compose" -vvv --extra-vars "target=test";

   if [ $? -eq 0 ];
   then
     echo "Ansible playbook was successful was successful!!"
     exit 0
   else
     echo "Failed to complete playbook!!"
     exit 1
   fi

else
    echo "************Running Ansible Playbook on Production*************";\
    cd ./ansible && ansible-playbook main.yml --tags "general,prod,compose"  -vvv --extra-vars "target=prod";

    if [ $? -eq 0 ];
       then
         echo "Ansible playbook was successful was successful!!"
         exit 0
       else
         echo "Failed to complete playbook!!"
         exit 1
       fi

fi




