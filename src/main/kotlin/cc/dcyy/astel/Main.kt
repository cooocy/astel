package cc.dcyy.astel

import mu.KotlinLogging

val L = KotlinLogging.logger {}

fun main() {
    L.info { "Hello Astel !!!" }
    val configs = loadConfig("config.yaml")
    print(configs)
}