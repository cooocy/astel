package cc.dcyy.astel.core

import cc.dcyy.astel.PersistentC
import cc.dcyy.astel.core.persistent.SnapshotChief
import mu.KotlinLogging
import java.nio.file.Paths

object AstelShutdown {

    private val L = KotlinLogging.logger {}

    fun shutdown(persistentC: PersistentC) {
        L.info { "Astel Shutdown... " }
        val path: String = persistentC.snapshot!!.path!!
        val p = Paths.get(path, "00.sn")
        L.info { "The Astel memory data will be serialized. path: $p" }
        SnapshotChief.serialize(p)
        L.info { "Astel Shutdown." }
    }

}