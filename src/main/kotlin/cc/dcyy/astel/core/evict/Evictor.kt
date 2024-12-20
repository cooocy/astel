package cc.dcyy.astel.core.evict

import cc.dcyy.astel.core.entry.Astel

/**
 * An evictor to evict the temporary keys and values in `ExpiresPool` and `Astel`.
 */
object Evictor {

    /**
     * Randomly evict some keys and values.
     */
    fun randomEvict() {
        val size = ExpiresPool.size()
        var chosen = size / 2
        if (size > 5000) {
            chosen = size / 3
        }
        if (size > 10000) {
            chosen = size / 10
        }
        if (size > 1000000) {
            chosen = size / 100
        }
        val chosenKeys = ExpiresPool.shuffle().take(chosen)
        for (key in chosenKeys) {
            Astel.removeIfExpired(key)
        }
    }

    /**
     * Full evict expired keys and values.
     */
    fun fullEvict() {
        for (key in ExpiresPool.copy()) {
            Astel.removeIfExpired(key)
        }
    }

}