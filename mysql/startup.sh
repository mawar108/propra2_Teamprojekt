#! /bin/bash

ls -la /etc/mysql/conf.d/
echo "setting permissions"
chmod 0444 /etc/mysql/conf.d/config.cnf
echo "done"
ls -la /etc/mysql/conf.d/

docker-entrypoint.sh mysqld
