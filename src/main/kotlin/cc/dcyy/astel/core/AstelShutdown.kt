package cc.dcyy.astel.core

import cc.dcyy.astel.core.persistent.serialize
import mu.KotlinLogging
import java.nio.file.Paths

object AstelShutdown {

    private val L = KotlinLogging.logger {}

    fun shutdown(configurations: Map<String, Any>) {
        L.info { "Astel Shutdown... " }
        val persistent: Map<String, Any> = configurations["persistent"] as Map<String, Any>
        val snapshot: Map<String, Any> = persistent["snapshot"] as Map<String, Any>
        val path: String = snapshot["path"] as String
        val p = Paths.get(path, "00.sn")
        L.info { "The Astel memory data will be serialized. path: $p" }
        serialize(p)
        L.info { "Astel Shutdown." }
    }

}