#!/bin/bash
SERVICE_NAME=spring-petclinic-2.1.6.RELEASE.jar
SERVER_PORT=8080

nohup java -jar /home/ec2-user/build/$SERVICE_NAME
