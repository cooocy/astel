package cc.dcyy.astel.server

import cc.dcyy.astel.Configurations
import cc.dcyy.astel.core.AstelInitializer
import cc.dcyy.astel.core.AstelShutdown
import cc.dcyy.astel.core.MemoryCleaner
import cc.dcyy.astel.core.persistent.SnapshotChief
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultThreadFactory
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * Do some setup.
 */
class Setup(val configurations: Configurations) {

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
            val path: String = configurations.persistent!!.snapshot!!.path!!
            val p = Paths.get(path, "00.sn")
            SnapshotChief.serialize(p)
        }, 30, configurations.persistent!!.snapshot!!.period!!, TimeUnit.SECONDS)

        // Do memory clean.
        scheduleEventLoopGroup.scheduleAtFixedRate({
            MemoryCleaner.clean(configurations.memoryClean!!)
        }, 60, configurations.memoryClean!!.period!!, TimeUnit.SECONDS)

    }

    /**
     * Register all shutdown hooks.
     */
    fun registerShutdown() {
        Runtime.getRuntime().addShutdownHook(Thread { AstelShutdown.shutdown(configurations.persistent!!) })
    }

}