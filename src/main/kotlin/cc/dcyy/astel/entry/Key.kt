package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

class Key {

    var key: String = ""                    // The original key set by customer, unique.
        private set
    var hash: Int = 0                       // The hash code of this key, used to partition, not unique, maybe duplicated.
        private set
    var expires: Instant = Instant.EPOCH    // Default value is `Instant.EPOCH`, means forever.
        private set

    companion object {

        /**
         * New a forever key.
         */
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

        /**
         * New a temporary key.
         */
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

    /**
     * Judge if the key is temporary, don't care if it has expired.
     */
    fun isTemporary(): Boolean {
        return expires.isAfter(Instant.EPOCH)
    }

    /**
     * Judge if the key is expired.
     */
    fun isExpired(): Boolean {
        return expires != Instant.EPOCH && expires.isBefore(Instant.now())
    }


    override fun toString(): String {
        return "Key(key='$key', hash=$hash, expires=$expires)"
    }

    /**
     * Only depends on the key.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key
        return key == other.key
    }

    override fun hashCode(): Int {
        return hash
    }

}