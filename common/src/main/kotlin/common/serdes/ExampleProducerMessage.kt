package common.serdes

import java.time.LocalDate

data class ExampleProducerMessage(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val country: String,
    val birthDate: LocalDate
)