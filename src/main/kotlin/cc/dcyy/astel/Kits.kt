package cc.dcyy.astel

/**
 * The JVM memory info.
 * Xms(min), Xmx(max).
 * If `Xms == Xmx`, total == max.
 */
data class Memory(
    val total: Long, val free: Long, val max: Long, val used: Long = total - free
)


/**
 * Get JVM memory info.
 */
fun memory(): Memory {
    val runtime = Runtime.getRuntime()
    return Memory(runtime.totalMemory(), runtime.freeMemory(), runtime.maxMemory())
}