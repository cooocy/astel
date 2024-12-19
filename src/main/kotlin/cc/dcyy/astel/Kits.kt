package cc.dcyy.astel

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

object AstelRuntime {

    /**
     * Get JVM memory info.
     */
    fun memory(): Memory {
        val runtime = Runtime.getRuntime()
        return Memory(runtime.totalMemory(), runtime.freeMemory(), runtime.maxMemory())
    }

}

object Jackson {
    private val jsonObjectMapper = ObjectMapper()
    private val yamlObjectMapper = ObjectMapper(YAMLFactory())

    fun toJson(any: Any): String {
        return jsonObjectMapper.writeValueAsString(any)
    }

    fun <T> fromYamlByFile(yamlFile: File, valueType: Class<T>): T {
        return yamlObjectMapper.readValue(yamlFile, valueType)
    }

}


/**
 * The JVM memory info.
 * Xms(min), Xmx(max).
 * If `Xms == Xmx`, total == max.
 */
data class Memory(
    val total: Long, val free: Long, val max: Long, val used: Long = total - free,
)
