#!/bin/bash
SERVICE_NAME=spring-petclnic
SERVER_PORT=8080

sudo systemctl daemon-reload
service ${SERVICE_NAME} restart

sleep 5 # for waitting stop daemon first

CONTINUE=1

while [ ${CONTINUE} -eq 1 ]
do
    sleep 1
    PORT_STATUS=`netstat -an | grep LISTEN | grep ":${SERVER_PORT}" | wc -l`
    if [ ${PORT_STATUS} -eq 1 ]
    then
        CONTINUE=0
    fi
done

