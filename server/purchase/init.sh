#!/usr/bin/env sh

log_path="/var/log/app"

if test ! -z "${EULER_APP_LOG_PATH}"
then
    log_path=${EULER_APP_LOG_PATH}
fi

if test ! -d "${log_path}"
then
  mkdir -p ${log_path}
  if test $? -ne 0; then
    exit 1;
  fi
fi

GC_LOG_OPTS="-Xloggc:${log_path}/gc.log -XX:+PrintGCDetails -XX:+PrintPromotionFailure -XX:+PrintGCApplicationStoppedTime \
-XX:NumberOfGCLogFiles=2 -XX:GCLogFileSize=64M"

SNAPSHOT_OPTS="-XX:+PrintCommandLineFlags -XX:ErrorFile=${log_path}/hs_err_pid%p.err \
-XX:HeapDumpPath=${log_path} -XX:+HeapDumpOnOutOfMemoryError"

OTHER_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dfile.encoding=UTF-8"

JAVA_OPTS="${JAVA_OPTS} ${EULER_JAVA_OPTS} ${GC_LOG_OPTS} ${SNAPSHOT_OPTS} ${OTHER_OPTS}"

echo "JAVA_OPTS: ${JAVA_OPTS}"

java ${JAVA_OPTS} -jar app.jar
