#!/bin/sh

rm -f tpid

nohup java -jar /opt/spring-boot-8080/maneger.jar --spring.profiles.active=dev > /dev/null 2>&1 &

echo $! > tpid

echo Start Success!