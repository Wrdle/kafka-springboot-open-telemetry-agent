package com.mattdag.examplekafkaproducer.config

import com.mattdag.examplekafkaproducer.component.KafkaProducer
import common.serdes.ExampleProducerMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaProducerConfig {

    @Value("\${app.producer.topic}")
    private lateinit var topic: String

    @Bean
    fun kafkaProducer(kafkaTemplate: KafkaTemplate<String, ExampleProducerMessage>): KafkaProducer {
        return KafkaProducer(topic, kafkaTemplate)
    }
}
