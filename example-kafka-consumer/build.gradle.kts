import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLoggingVersion: String by project
val jacksonKotlinVersion: String by project
val lokiLogbackVersion: String by project

plugins {
	id("idea")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("kapt")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("io.gitlab.arturbosch.detekt")
}

group = "com.mattdag"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":common"))
	implementation ("com.github.loki4j:loki-logback-appender:$lokiLogbackVersion")
	implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
