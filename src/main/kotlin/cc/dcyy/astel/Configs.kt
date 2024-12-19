package cc.dcyy.astel

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.File

object Configs {

    /**
     * Load configs in resources.
     */
    fun loadConfig(path: String): Configurations {
        return Jackson.fromYamlByFile(File(ClassLoader.getSystemResource(path).file), Configurations::class.java)
    }

}

class Configurations {
    var persistent: PersistentC? = null

    @JsonProperty("memory-clean")
    var memoryClean: MemoryCleanC? = null
    var server: ServerC? = null
}

class PersistentC {
    var snapshot: SnapshotC? = null
}

class SnapshotC {
    var path: String? = null
    var period: Long? = null
}

class MemoryCleanC {
    var threshold: Int? = null
    var period: Long? = null
}

class ServerC {
    var port: Int? = null
}