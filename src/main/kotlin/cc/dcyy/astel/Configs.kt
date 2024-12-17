package cc.dcyy.astel

import org.yaml.snakeyaml.Yaml

/**
 * Load configs in resources.
 */
fun loadConfig(path: String): Configurations {
    return Yaml().loadAs(ClassLoader.getSystemResourceAsStream(path), Configurations::class.java)
}

class Configurations(var persistent: PersistentC? = null, var memory: MemoryC? = null)

class PersistentC(var snapshot: SnapshotC? = null)

class SnapshotC(var path: String? = null)

class MemoryC(var threshold: Int? = null)