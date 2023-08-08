#!/bin/bash

KAFKA_PRODUCER_JAR="jars/kafka-producer.jar"
OTEL_AGENT_JAR="jars/otel-javaagent.jar"

cd ..

# Stop OTEL agent from exporting metrics. We can scrape these from the SpringBoot actuator.
export OTEL_METRICS_EXPORTER=none

jar_file1=$KAFKA_PRODUCER_JAR
jar_file2=$OTEL_AGENT_JAR

# Function to check if a JAR file exists and run it with the second JAR file as a Java agent
function run_jar_with_agent() {
    local main_jar="$1"
    local agent_jar="$2"

    if [ -e "$main_jar" ]; then
        echo "Main JAR file '$main_jar' found."
        if [ -e "$agent_jar" ]; then
            echo "Agent JAR file '$agent_jar' found."
            java -javaagent:"$agent_jar" -Dotel.metrics.exporter=prometheus -Dotel.exporter.prometheus.port=9464 -jar "$main_jar"
        else
            echo "Agent JAR file '$agent_jar' not found. Run setup.sh to download the agent."
            java -jar "$main_jar"
        fi
    else
        echo "Main JAR file '$main_jar' not found. Run setup.sh to build the JAR file."
    fi
}

# Check and run the first JAR file with the second JAR file as an agent
run_jar_with_agent "$jar_file1" "$jar_file2"
