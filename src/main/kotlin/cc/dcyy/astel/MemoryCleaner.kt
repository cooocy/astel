package cc.dcyy.astel

import cc.dcyy.astel.evict.Evictor
import mu.KotlinLogging
import java.time.Duration
import java.time.Instant

object MemoryCleaner {

    val L = KotlinLogging.logger {}

    /**
     * Clean all temporary keys and values.
     */
    fun clean(threshold: Int) {
        val before = memory()
        val usedPercentBefore = before.used / before.total
        if (usedPercentBefore < threshold) {
            L.info { "No need to clean. Current memory used: ${usedPercentBefore}%, threshold: $threshold" }
            return
        }
        val usedMbBefore = before.used / (1014 * 1024)
        L.info { "Clean will begin. Current memory used: ${usedMbBefore}MB, percent: $usedPercentBefore" }

        val begin = Instant.now()
        Evictor.fullEvict()
        val after = memory()
        val usedPercentAfter = after.used / after.total
        val usedMbAfter = after.used / (1014 * 1024)
        L.info { "Clean end. Current memory used: ${usedMbAfter}MB, percent: $usedPercentAfter, released: ${usedMbBefore - usedMbAfter}MB." }

        val end = Instant.now()
        val const = Duration.between(begin, end).toMillis()
        L.info { "Clean end. const: $const" }
    }

}