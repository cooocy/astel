package cc.dcyy.astel.entry.evict

import cc.dcyy.astel.entry.Root

/**
 * A evictor to evict the temporary keys and values in `ExpiresPool` and `Root`.
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
            if (key.isExpired()) {
                ExpiresPool.remove(key)
                Root.remove(key)
            }
        }
    }

}