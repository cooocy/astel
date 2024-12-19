package cc.dcyy.astel.core

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.persistent.SnapshotChief
import mu.KotlinLogging
import java.nio.file.Paths

object AstelShutdown {

    private val L = KotlinLogging.logger {}

    fun shutdown(persistentC: PersistentC) {
        L.info { "Astel Shutdown... " }
        val dir = Paths.get(persistentC.snapshot!!.path!!)
        L.info { "The Astel memory data will be serialized. path: $dir" }
        SnapshotChief.serialize(dir)
        L.info { "Astel Shutdown." }
    }

}