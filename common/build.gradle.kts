import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("idea")
	kotlin("jvm")
	kotlin("kapt")
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

dependencies { }

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
