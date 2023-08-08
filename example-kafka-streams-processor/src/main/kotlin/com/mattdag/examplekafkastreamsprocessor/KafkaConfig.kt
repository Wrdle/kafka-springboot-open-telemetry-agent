package com.mattdag.examplekafkastreamsprocessor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import common.serdes.ExampleProducerMessage
import common.serdes.ExampleTransformedMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import java.time.LocalDate
import java.time.Period

@Configuration
@EnableKafkaStreams
class KafkaConfig(
    @Value("\${app.streams.inbound-topic}") private val inboundTopic: String,
    @Value("\${app.streams.outbound-topic}") private val outboundTopic: String,
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()

    @Bean
    fun kafkaStreamsBuilder(streamsBuilder: StreamsBuilder): KStream<String, ExampleProducerMessage> =
        streamsBuilder.stream<String, ExampleProducerMessage>(inboundTopic).apply {
            this.peek { k, _ -> logger.info { "Consumed and processing message with key=[$k]" } }
                .mapValues { value -> mapToTransformedMessage(value) }
                .filter { _, value -> value != null }
                .peek { k, _ -> logger.info { "Forwarding message with key=[$k]" } }
                .to(outboundTopic)
        }

    fun mapToTransformedMessage(value: ExampleProducerMessage?): ExampleTransformedMessage? {
        if (value == null) {
            return null
        }

        val fullName = "${value.firstname} ${value.lastname}"
        val age = Period.between(value.birthDate, LocalDate.now()).years

        return ExampleTransformedMessage(
            value.userId,
            fullName,
            age
        )
    }
}
