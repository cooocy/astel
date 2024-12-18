package cc.dcyy.astel.server

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.AstelInitializer
import cc.dcyy.astel.core.AstelShutdown
import cc.dcyy.astel.core.persistent.SnapshotChief
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultThreadFactory
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * Do some setup.
 */
object Setup {

    /**
     * Do some init.
     */
    fun initialize(persistentC: PersistentC) {
        AstelInitializer.init(persistentC)
    }

    /**
     * Register all schedule tasks.
     */
    fun registerSchedule(persistentC: PersistentC) {
        // Do persistent every n seconds, delay 30s after starting.
        val scheduleEventLoopGroup = NioEventLoopGroup(1, DefaultThreadFactory("netty-astel-schedule", true))
        scheduleEventLoopGroup.scheduleAtFixedRate({
            val path: String = persistentC.snapshot!!.path!!
            val p = Paths.get(path, "00.sn")
            SnapshotChief.serialize(p)
        }, 30, persistentC.snapshot!!.every!!, TimeUnit.SECONDS)
    }

    /**
     * Register all shutdown hooks.
     */
    fun registerShutdown(persistentC: PersistentC) {
        Runtime.getRuntime().addShutdownHook(Thread { AstelShutdown.shutdown(persistentC) })
    }

}