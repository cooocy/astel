package cc.dcyy.astel.core.evict

import cc.dcyy.astel.core.entry.Key

/**
 * All the keys of temporary values were put in this pool.
 * Scan this pool regularly to clean up the expired keys and values.
 */
object ExpiresPool {

    private val set = LinkedHashSet<Key>()

    fun put(key: Key) {
        if (set.contains(key)) {
            set.remove(key)
        }
        set.add(key)
    }

    fun remove(key: Key) {
        set.remove(key)
    }

    /**
     * Returns a new list with the keys of this pool randomly shuffled.
     */
    fun shuffle(): List<Key> {
        return set.shuffled()
    }

    /**
     * Returns a copy: a `set` of expired keys.
     */
    fun copy(): List<Key> {
        return set.toList()
    }

    fun size(): Int {
        return set.size
    }

    fun clear() {
        set.clear()
    }

}