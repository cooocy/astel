package cc.dcyy.astel

import org.yaml.snakeyaml.Yaml

object Configs {

    /**
     * Load configs in resources.
     */

    fun loadConfig(path: String): Configurations {
        return Yaml().loadAs(ClassLoader.getSystemResourceAsStream(path), Configurations::class.java)
    }

}

class Configurations(var persistent: PersistentC? = null, var memoryClean: MemoryCleanC? = null, var server: ServerC? = null)

class PersistentC(var snapshot: SnapshotC? = null)

class SnapshotC(var path: String? = null, var period: Long? = null)

class MemoryCleanC(var threshold: Int? = null, var period: Long? = null)

class ServerC(var port: Int? = null)