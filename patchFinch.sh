#!/bin/sh

cd `dirname "$0"`/..

classpath=obj:../bin:../ecj/obj:../bc/obj:../asm/obj:../util/obj:../util/lib/commons-cli-1.2/commons-cli-1.2.jar:../util/lib/commons-logging-1.1.1/commons-logging-1.1.1.jar
mainclass=esi.finch.FinchExperiment

exec $JAVA_HOME/bin/java -server -ea -Xmx632m -classpath "$classpath" "$mainclass" "$@"
