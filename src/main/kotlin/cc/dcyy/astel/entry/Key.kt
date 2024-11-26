package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

class Key {

    var key: String = ""
        private set
    var hash: Int = 0
        private set
    var expires: Instant = Instant.EPOCH    // default value, equivalent to null
        private set

    companion object {

        fun new(k: String): Key {
            if (k.isBlank()) {
                throw IllegalArgumentException("Key cannot be empty.")
            }
            val key = Key()
            key.key = k
            key.hash = hash(k)
            Instant.now().plus(12, ChronoUnit.DAYS)
            return key
        }

        fun new(k: String, expire: Long, unit: TemporalUnit): Key {
            val key = new(k)
            key.expires = Instant.now().plus(expire, unit)
            return key
        }

        private fun hash(key: String): Int {
            var h: Int
            return if (key.isBlank()) 0 else (key.hashCode().also { h = it }) xor (h ushr 16)
        }

    }


    override fun toString(): String {
        return "Key(key='$key', hash=$hash, expires=$expires)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key

        if (key != other.key) return false
        if (hash != other.hash) return false

        return true
    }

    override fun hashCode(): Int {
        return hash
    }

}