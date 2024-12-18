plugins {
    kotlin("jvm") version "2.0.20"
}

group = "cc.dcyy"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.esotericsoftware:kryo:5.6.2")
    // facade and logback
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("org.yaml:snakeyaml:2.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("io.netty:netty-all:4.1.115.Final")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}