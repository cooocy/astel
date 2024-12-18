package cc.dcyy.astel.server

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.persistent.serialize
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultThreadFactory
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * Register all schedule tasks.
 */
object Schedule {

    fun schedule(persistentC: PersistentC) {
        // Do persistent every n seconds, delay 30s after starting.
        val scheduleEventLoopGroup = NioEventLoopGroup(1, DefaultThreadFactory("netty-astel-schedule", true))
        scheduleEventLoopGroup.scheduleAtFixedRate({
            val path: String = persistentC.snapshot!!.path!!
            val p = Paths.get(path, "00.sn")
            serialize(p)
        }, 30, persistentC.snapshot!!.every!!, TimeUnit.SECONDS)
    }

}