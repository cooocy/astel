package cc.dcyy.astel.core.entry

import cc.dcyy.astel.AstelFillException
import cc.dcyy.astel.KeyNotFoundException
import cc.dcyy.astel.core.evict.ExpiresPool
import java.util.HashMap
import mu.KotlinLogging
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

object Astel {
    val tbl = arrayOfNulls<HashMap<Key, Value>>(TBL_SIZE)
    private const val TBL_SIZE: Int = 16
    private val L = KotlinLogging.logger {}

    /**
     * Put the key and value.
     */
    fun put(key: Key, value: Value) {
        val index = indexOf(key)
        var hm = tbl[index]
        if (hm == null) {
            hm = HashMap<Key, Value>()
            tbl[index] = hm
        }
        hm[key] = value

        if (value.isTemporary()) {
            ExpiresPool.put(key)
        }
    }

    /**
     * Get the value by its key if exists.
     * When the expired value is got, the key and the value will be removed, and the key will be removed from ExpiresPool too.
     */
    fun get(key: Key): Value? {
        val index = indexOf(key)
        val value = tbl[index]?.get(key) ?: return null
        if (value.isExpired()) {
            ExpiresPool.remove(key)
            tbl[index]?.remove(key)
            return null
        }
        return value
    }

    /**
     * Remove the key and its value if exists.
     */
    fun remove(key: Key) {
        val value = tbl[indexOf(key)]?.remove(key) ?: return
        if (value.isTemporary()) {
            ExpiresPool.remove(key)
        }
    }

    /**
     * Remove the key and value if expired.
     * The key will be removed from ExpiresPool too.
     */
    fun removeIfExpired(key: Key) {
        val index = indexOf(key)
        val value = tbl[index]?.get(key) ?: return
        if (value.isExpired()) {
            ExpiresPool.remove(key)
            tbl[index]?.remove(key)
        }
    }


    /**
     * If contains this key.
     */
    fun contains(key: Key): Boolean {
        val index = indexOf(key)
        return tbl[index]?.containsKey(key) ?: false
    }

    /**
     * Return all the keys size.
     */
    fun size(): Int {
        return tbl.sumOf { it?.size ?: 0 }
    }

    /**
     * Set the expire time for spec key. The expire time is ${seconds} seconds after current.
     */
    fun expire(key: Key, seconds: Long) {
        var value = get(key) ?: throw KeyNotFoundException()
        value.expires = Instant.now().plusSeconds(seconds.toLong())
        if (value.isExpired()) {
            ExpiresPool.put(key)
        }
    }

    /**
     * Get the expire seconds. 0 means expired or the key not exists. -1 means forever.
     */
    fun ttl(key: Key): Long {
        val value = get(key) ?: return 0
        if (value.isTemporary()) {
            return Duration.between(Instant.now(), value.expires).get(ChronoUnit.SECONDS)
        }
        return -1
    }

    /**
     * Clear all keys and values.
     */
    fun clear() {
        L.info { "Astel cleared. Original size: ${size()}" }
        tbl.fill(null)
    }

    /**
     *  Clear all keys and values, and then fill Astel by the specified array.
     */
    fun clearAndFill(newTbl: Array<HashMap<Key, Value>?>) {
        L.info { "Astel will clear and fill. Original size: ${size()}" }
        if (newTbl.size != TBL_SIZE) {
            throw AstelFillException("New tbl size is ${newTbl.size}, not eq to ${TBL_SIZE}.")
        }
        tbl.fill(null)
        for (i in 0 until tbl.size) {
            tbl[i] = newTbl[i]
        }
        L.info { "Astel filled. New size: ${size()}" }
    }

    private fun indexOf(key: Key): Int {
        return key.hash and (TBL_SIZE - 1)
    }

}

