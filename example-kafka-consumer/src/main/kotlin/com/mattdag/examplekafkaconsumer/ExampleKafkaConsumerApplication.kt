package com.mattdag.examplekafkaconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleKafkaConsumerApplication

fun main(args: Array<String>) {
	runApplication<ExampleKafkaConsumerApplication>(args = args)
}
