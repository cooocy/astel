package cc.dcyy.astel.server

import cc.dcyy.astel.Configurations
import cc.dcyy.astel.core.AstelInitializer
import cc.dcyy.astel.core.AstelShutdown
import cc.dcyy.astel.core.MemoryCleaner
import cc.dcyy.astel.core.persistent.SnapshotChief
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultThreadFactory
import mu.KotlinLogging
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * Do some setup.
 */
class Setup(val configurations: Configurations) {

    private val L = KotlinLogging.logger {}

    /**
     * Do some init.
     */
    fun initialize() {
        AstelInitializer.init(configurations.persistent!!)
    }

    /**
     * Register all schedule tasks.
     */
    fun registerSchedule() {
        // Do persistent every n seconds, delay 30s after starting.
        val scheduleEventLoopGroup = NioEventLoopGroup(1, DefaultThreadFactory("netty-astel-schedule", true))
        scheduleEventLoopGroup.scheduleAtFixedRate({
            try {
                val p = Paths.get(configurations.persistent!!.snapshot!!.path!!)
                SnapshotChief.serialize(p)
            } catch (e: Exception) {
                L.error { e }
            }
        }, 30, configurations.persistent!!.snapshot!!.period!!, TimeUnit.SECONDS)


        // Do memory clean.
        scheduleEventLoopGroup.scheduleAtFixedRate({
            try {
                MemoryCleaner.clean(configurations.memoryClean!!)
            } catch (e: Exception) {
                L.error { e }
            }
        }, 60, configurations.memoryClean!!.period!!, TimeUnit.SECONDS)

    }

    /**
     * Register all shutdown hooks.
     */
    fun registerShutdown() {
        Runtime.getRuntime().addShutdownHook(Thread({
            try {
                AstelShutdown.shutdown(configurations.persistent!!)
            } catch (e: Exception) {
                L.error { e }
            }
        }, "server-shutdown"))
    }

}