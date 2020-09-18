#!/bin/bash
SERVICE_NAME=spring-petclinic-2.1.6.RELEASE.jar
SERVER_PORT=8080

echo "> 현재 실행중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $SERVICE_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $APPLICATION_JAR 배포"
nohup java -jar /home/ec2-user/build/spring-petclinic-2.1.6.RELEASE.jar > /dev/null 2> /dev/null < /dev/null &
