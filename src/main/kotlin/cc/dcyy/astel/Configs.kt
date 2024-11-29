package cc.dcyy.astel

import org.yaml.snakeyaml.Yaml

/**
 * Load configs in resources.
 */
fun loadConfig(path: String): Map<String, Any> {
    return Yaml().load<Map<String, Any>>(ClassLoader.getSystemResourceAsStream(path))
}