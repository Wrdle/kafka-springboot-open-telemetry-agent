package com.mattdag.examplekafkastreamsprocessor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleKafkaStreamsProcessorApplication

fun main(args: Array<String>) {
	runApplication<ExampleKafkaStreamsProcessorApplication>(args = args)
}
