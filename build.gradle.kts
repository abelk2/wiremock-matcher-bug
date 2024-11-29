plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.genesys.wiremock.repro.matcherbug"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.wiremock:wiremock:3.9.2")
    implementation("io.ktor:ktor-client-core-jvm:3.0.1")
    implementation("org.slf4j:slf4j-simple:2.0.16")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}