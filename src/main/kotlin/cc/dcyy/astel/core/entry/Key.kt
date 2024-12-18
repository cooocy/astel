package cc.dcyy.astel.core.entry

class Key private constructor() {

    var k: String = ""                    // The original key set by customer, unique.
        private set
    var hash: Int = 0                       // The hash code of this key, used to partition, not unique, maybe duplicated.
        private set

    companion object {
        /**
         * New a key.
         */
        fun new(k: String): Key {
            if (k.isBlank()) {
                throw IllegalArgumentException("Key cannot be empty.")
            }
            val key = Key()
            key.k = k
            key.hash = hash(k)
            return key
        }

        private fun hash(key: String): Int {
            var h: Int
            return if (key.isBlank()) 0 else (key.hashCode().also { h = it }) xor (h ushr 16)
        }

    }

    override fun toString(): String {
        return "Key(key='$k', hash=$hash)"
    }

    /**
     * Only depends on the key.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key
        return k == other.k
    }

    override fun hashCode(): Int {
        return hash
    }

}