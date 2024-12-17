package cc.dcyy.astel.core

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.persistent.deserialize
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths

/**
 * The astel initializer.
 */
object AstelInitializer {

    private val L = KotlinLogging.logger {}

    fun init(persistentC: PersistentC) {
        L.info { "Astel Starting..." }
        val path: String = persistentC.snapshot!!.path!!
        val p = Paths.get(path, "00.sn")
        if (Files.exists(p)) {
            L.info { "The snapshot will be deserialized. path: $p" }
            deserialize(p)
            L.info { "Astel Started." }
        } else {
            L.info { "Astel Started. No snapshot." }
        }
    }

}