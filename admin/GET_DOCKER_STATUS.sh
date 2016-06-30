#!/bin/sh
# del flow
#SSHPASS="sshpass -p dir7413" # WARNING: administrator's password
#SSH="ssh -o StrictHostKeyChecking=no"
#${SSHPASS} ${SSH} root@10.0.0.10 ovs-ofctl del-flows s1
#${SSHPASS} ${SSH} root@10.0.0.20 ovs-ofctl del-flows s2


GET=$1
#docker ps -a --format "table {{.Names}}@{{.Ports}}" > ${GET}
echo "test" > ${GET}
echo "10.0.0.10" >> ${GET}
echo "10.0.0.20" >> ${GET}
