package com.mattdag.examplekafkaconsumer.component

import common.serdes.ExampleTransformedMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener {

    val logger = KotlinLogging.logger {}

    @KafkaListener(topics = ["\${app.consumer.topic}"])
    fun listenMessages(message: ExampleTransformedMessage) {
        logger.info { "Consumed message: [$message]" }

        // Here is where you would then do something with the message such as save to a DB
    }
}
