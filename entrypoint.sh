#!/bin/sh

exec java ${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -jar -Djava.security.egd=file:/dev/./urandom task-list-api.jar