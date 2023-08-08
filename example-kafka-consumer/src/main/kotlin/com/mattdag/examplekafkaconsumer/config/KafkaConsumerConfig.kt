package com.mattdag.examplekafkaconsumer.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import common.serdes.ExampleTransformedMessage
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()

    @Bean
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<in String, in ExampleTransformedMessage> {
        return DefaultKafkaConsumerFactory(kafkaProperties.buildConsumerProperties())
    }

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<in String, in ExampleTransformedMessage>
    ): ConcurrentKafkaListenerContainerFactory<String, ExampleTransformedMessage>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ExampleTransformedMessage>()
        factory.consumerFactory = consumerFactory
        return factory
    }
}
