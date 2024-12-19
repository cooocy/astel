package cc.dcyy.astel.core

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.persistent.SnapshotChief
import mu.KotlinLogging
import java.nio.file.Paths

/**
 * The astel initializer.
 */
object AstelInitializer {

    private val L = KotlinLogging.logger {}

    fun init(persistentC: PersistentC) {
        L.info { "Astel Starting..." }
        val dir = Paths.get(persistentC.snapshot!!.path!!)
        L.info { "The snapshot will be deserialized. dir: $dir" }
        SnapshotChief.deserialize(dir)
        L.info { "Astel Started." }
    }

}