import os
import shutil
import subprocess
import sys
import time


def installAnsiblePosix():
    os.system(' echo "************Installing Ansible.posix library************"; \
                ansible-galaxy collection install ansible.posix; \
                ansible-galaxy collection install ansible.posix ')


def createSSHDirectory():
    # echo = 'echo "************Creating SSH Directory************"'
    # command = echo; 'mkdir -p ~/.ssh;'  'echo "$SSH_KEY" > ../private.key;' 'sudo chmod 600 ../private.key;' \
    #                 'eval $(ssh-agent) > /dev/null;' ' killall ssh-agent;' 'ssh-add ../private.key'
    # os.system(command)
    os.system(' echo "************Creating SSH Directory************"; \
                mkdir -p ~/.ssh/; \
                echo "$SSH_KEY" > ../private.key; \
                sudo chmod 600 ../private.key; \
                eval $(ssh-agent) > /dev/null; \
                killall ssh-agent; \
                eval `ssh-agent`; \
                ssh-add ../private.key ')
    os.system('ls')
    os.system('cat $SSH_KEY_PATH')




def installZerotier():
    os.system(' echo "************Installing Zerotier************"; \
              curl -s https://install.zerotier.com | sudo bash; \
              sudo chmod 660 /var/lib/zerotier-one/authtoken.secret; \
              sudo zerotier-cli join 1d719394046717be; ')


def manipulateFile():

    os.system(' echo "************Copying jar File to the foodmgt directory************";\
              cp target/foodmgt-0.0.1-SNAPSHOT.jar ansible/roles/docker/files/foodmgt/; \
              ls ansible/roles/docker/files/foodmgt/; \
            ')



    # os.system(' echo " " ;\
    #             echo "************Copying jar File to the foodmgt directory************"; \
    #             cd ansible/roles/docker/files/foodmgt; \
    #             md5sum foodmgt-0.0.1-SNAPSHOT.jar; \
    #             ls ../../../../../../target ;\
    #             cp ../../../../../../target/foodmgt-0.0.1-SNAPSHOT.jar .;\
    #             ls /home/runner/work/foodmgt/foodgmt/; \
    #             md5sum foodmgt-0.0.1-SNAPSHOT.jar; \
    # ')


def runPlaybook():
    os.chdir('ansible')
    time.sleep(15)
    ping = os.system('ping -c2 10.147.18.5')
    print(f'*******Server status is ***********{ping}')
    if ping == 0:
        os.system('echo "ping was successful!!"')

        if os.system('sudo chmod +x ../branch_testing.sh;\
                  cd .. && ./branch_testing.sh') == 0:
            print("Script has been run successfully!")
        else:
            print("Failed to run script!")
            sys.exit(1)

    else:
        print('Failed to connect to host, ping was unsuccessful!!')
        sys.exit(1)


if __name__=="__main__":
    installAnsiblePosix()
    createSSHDirectory()
    installZerotier()
    manipulateFile()
    runPlaybook()




    # branch_name = os.system('$BRANCH_NAME')
    # print(f' this is the branch name {branch_name}')
    # os.system('\
    #          ')
    #     ansible = os.system(' echo "************Running Ansible Playbook*************";\
    #             ansible-playbook main.yml --tags compose -vvv --extra-vars "target=test" ')
    # else:
    #     ansible = os.system(' echo "************Running Ansible Playbook*************";\
    #             ansible-playbook main.yml --tags compose -vvv --extra-vars "target=prod" ')
    #
    #
    # if ansible == 0:
    #     os.system('echo "ansible playbook was successful was successful!!"')
    # else:
    #     print('Failed to complete playbook!!')
    #     sys.exit(1)