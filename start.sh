#!/bin/sh

echo '------------------------------------ start.sh begin ------------------------------------'

gradle build -x test
echo 'ts:' `date "+%Y%m%d-%H:%M:%S.%3N"` 'Compile Success'

JVM_PID=$(jcmd | grep butterfly.jar | grep -v grep | awk '{print $1}')
if [ -z "${JVM_PID}" ]
then
echo 'ts:' `date "+%Y%m%d-%H:%M:%S.%3N"` 'No Process'
else
kill ${JVM_PID}
echo 'ts:' `date "+%Y%m%d-%H:%M:%S.%3N"` 'Killed' ${JVM_PID}
fi

nohup java -jar build/libs/astel-1.0.jar > /dev/null &

echo '------------------------------------ start.sh end ------------------------------------'