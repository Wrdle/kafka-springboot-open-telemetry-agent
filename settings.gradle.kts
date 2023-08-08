rootProject.name = "kafka-otel-example"

pluginManagement {

    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val detektVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion
    }

    repositories {
        gradlePluginPortal()
    }
}

include("common")
include("example-kafka-consumer")
include("example-kafka-streams-processor")
include("example-kafka-producer")
