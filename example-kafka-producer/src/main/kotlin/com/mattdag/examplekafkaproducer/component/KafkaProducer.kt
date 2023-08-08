package com.mattdag.examplekafkaproducer.component

import common.serdes.ExampleProducerMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate


class KafkaProducer(
    private val topic: String,
    private val kafkaTemplate: KafkaTemplate<String, ExampleProducerMessage>,
) {

    private val logger = KotlinLogging.logger {}

    fun send(payload: ExampleProducerMessage) {
        val key = payload.userId
        val result = kafkaTemplate.send(topic, key, payload)
        result.thenApplyAsync {
            logger.info { "Message sent with key=[${it.producerRecord.key()}] offset=[${it.recordMetadata.offset()}]" }
        }
    }
}
