package cc.dcyy.astel.entry

import java.util.HashMap

object Root {
    private const val TBL_SIZE: Int = 16
    private val tbl = arrayOfNulls<HashMap<Key, Value>>(TBL_SIZE)

    fun put(key: Key, value: Value) {
        val index = indexOf(key)
        val hm = getSlotHead(index)
        hm[key] = value
    }

    fun get(key: Key): Value? {
        val index = indexOf(key)
        return tbl[index]?.get(key)
    }

    fun contains(key: Key): Boolean {
        val index = indexOf(key)
        return tbl[index]?.containsKey(key) ?: false
    }

    fun size(): Int {
        return tbl.sumOf { it?.size ?: 0 }
    }

    private fun indexOf(key: Key): Int {
        return key.hash and (TBL_SIZE - 1)
    }

    private fun getSlotHead(index: Int): HashMap<Key, Value> {
        var hm = tbl[index]
        if (hm == null) {
            hm = HashMap<Key, Value>()
            tbl[index] = hm
        }
        return hm
    }

}

