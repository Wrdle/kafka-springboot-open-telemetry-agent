package com.mattdag.examplekafkaproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleKafkaProducerApplication

fun main(args: Array<String>) {
	runApplication<ExampleKafkaProducerApplication>(args = args)
}
