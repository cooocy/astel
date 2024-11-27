package cc.dcyy.astel.evict

import cc.dcyy.astel.Astel

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
            Astel.get(key)
            // No need to call under lines, Astel.get() has removed the expired keys and values.
//            if (value.isExpired()) {
//                ExpiresPool.remove(key)
//                Astel.remove(key)
//            }
        }
    }

}