package com.mattdag.examplekafkaproducer

import com.mattdag.examplekafkaproducer.component.KafkaProducer
import common.serdes.ExampleProducerMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import net.datafaker.Faker
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class Runner(
    @Value("\${app.producer.repetitions:1000}") private val repetitions: Int,
    @Value("\${app.producer.sendDelayMs:1000}") private val sendDelayMs: Long,
    private val producer: KafkaProducer
) : CommandLineRunner {

    private val logger = KotlinLogging.logger {}
    private val faker = Faker()

    override fun run(vararg args: String?) {
        logger.info { "Starting message producing with repetitions=[$repetitions]" }
        repeat(repetitions) {
            val message = createMessage()
            producer.send(message)
            Thread.sleep(sendDelayMs)
        }
        logger.info { "Completed producing messages" }
    }

    fun createMessage(): ExampleProducerMessage {
        return ExampleProducerMessage(
            userId = UUID.randomUUID().toString(),
            firstname = faker.name().firstName(),
            lastname = faker.name().lastName(),
            country = faker.country().countryCode2(),
            birthDate = faker.date().birthday().toLocalDateTime().toLocalDate()
        )
    }
}
