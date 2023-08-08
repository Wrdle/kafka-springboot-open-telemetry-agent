#!/bin/bash

# Define the URL to download the open-telemetry.jar from
OPEN_TELEMETRY_URL="https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.28.0/opentelemetry-javaagent.jar"
KAFKA_CONSUMER_JAR="kafka-consumer.jar"
KAFKA_STREAMS_PROCESSOR_JAR="kafka-streams-processor.jar"
KAFKA_PRODUCER_JAR="kafka-producer.jar"

cd ..

# Check if the jars folder exists; if not, create it
if [ ! -d "jars" ]; then
  mkdir jars
fi

# Function to check if the specified JAR files exist
check_jars_exist() {
  if [ ! -f "jars/$KAFKA_CONSUMER_JAR" ] || [ ! -f "jars/$KAFKA_STREAMS_PROCESSOR_JAR" ] || [ ! -f "jars/$KAFKA_PRODUCER_JAR" ]; then
    return 1
  fi

  return 0
}


# Check if the otel-javaagent.jar already exists in the jars folder
if [ ! -f "jars/otel-javaagent.jar" ]; then
  echo "Downloading otel-javaagent.jar..."
  # Use curl to download the jar file from the specified URL and save it in the jars folder
  curl -L -o "jars/otel-javaagent.jar" "$OPEN_TELEMETRY_URL"

  # Check if the download was successful
  if [ $? -eq 0 ]; then
    echo "Download completed successfully."
  else
    echo "Error: Failed to download otel-javaagent.jar."
    exit 1
  fi
else
  echo "otel-javaagent.jar already exists in the jars folder."
fi


if ! check_jars_exist; then
  echo "One or more required JAR files are missing. Running gradlew clean bootJar..."

  ./gradlew clean bootJar
  cp example-kafka-consumer/build/libs/example-kafka-consumer-0.0.1-SNAPSHOT.jar "jars/$KAFKA_CONSUMER_JAR"
  cp example-kafka-producer/build/libs/example-kafka-producer-0.0.1-SNAPSHOT.jar "jars/$KAFKA_PRODUCER_JAR"
  cp example-kafka-streams-processor/build/libs/example-kafka-streams-processor-0.0.1-SNAPSHOT.jar "jars/$KAFKA_STREAMS_PROCESSOR_JAR"

  if check_jars_exist; then
      echo "JAR files built successfully."
  else
    echo "Error: Failed to build JAR files and move them into/jars"
    exit 1
  fi
else
  echo "All required JAR files exist in the jar folder."
fi


echo "Starting docker containers..."
docker-compose up -d
