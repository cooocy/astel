package cc.dcyy.astel.core

import cc.dcyy.astel.AstelRuntime
import cc.dcyy.astel.MemoryCleanC
import cc.dcyy.astel.core.evict.Evictor
import mu.KotlinLogging
import java.time.Duration
import java.time.Instant

/**
 * A Memory Cleaner to clean temporary keys and values, supported by The Evictor.
 *
 */
object MemoryCleaner {

    val L = KotlinLogging.logger {}

    /**
     * Clean the memory by evictor.
     * If memory used > threshold, clean all temporary keys; else do random clean.
     */
    fun clean(c: MemoryCleanC) {
        L.info("Clean Begin...")
        val before = AstelRuntime.memory()
        val usedPercentBefore = before.used / before.total
        val usedMbBefore = before.used / (1014 * 1024)
        L.info { "Current memory: Used = ${usedMbBefore}MB, UsedPercent = $usedPercentBefore; Threshold: ${c.threshold}" }

        val begin = Instant.now()
        if (usedPercentBefore < c.threshold!!) {
            L.info { "Do random clean..." }
            Evictor.randomEvict()
        } else {
            L.info { "Do full clean..." }
            Evictor.fullEvict()
        }

        val after = AstelRuntime.memory()
        val usedPercentAfter = after.used / after.total
        val usedMbAfter = after.used / (1014 * 1024)
        val end = Instant.now()
        val const = Duration.between(begin, end).toMillis()
        L.info { "Clean End. const: ${const}ms. Current memory: Used = ${usedMbAfter}MB, UsedPercent = $usedPercentAfter, Released: ${usedMbBefore - usedMbAfter}MB." }
    }

}