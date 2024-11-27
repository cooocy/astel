package cc.dcyy.astel

import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Value
import cc.dcyy.astel.evict.ExpiresPool
import java.util.HashMap

object Astel {
    private const val TBL_SIZE: Int = 16
    val tbl = arrayOfNulls<HashMap<Key, Value>>(TBL_SIZE)

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
     * Remove the key and its value if exists.
     */
    fun remove(key: Key) {
        val value = tbl[indexOf(key)]?.remove(key) ?: return
        if (value.isTemporary()) {
            ExpiresPool.remove(key)
        }
    }

    /**
     * Clear all keys and values.
     */
    fun clear() {
        tbl.fill(null)
    }

    /**
     * Get the value by its key if exists.
     * When the expired value is got, the key and the value will be removed.
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
     * Judge if contains this key.
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

    fun clearAndFill(newTbl: Array<HashMap<Key, Value>?>) {
        if (newTbl.size != TBL_SIZE) {
            throw AstelFillException("New tbl size is ${newTbl.size}, not eq to ${TBL_SIZE}.")
        }
        tbl.fill(null)
        // TODO logger
        for (i in 0 until tbl.size) {
            tbl[i] = newTbl[i]
        }
    }

    private fun indexOf(key: Key): Int {
        return key.hash and (TBL_SIZE - 1)
    }

}

