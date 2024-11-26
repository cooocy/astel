package cc.dcyy.astel.entry

import java.time.Instant

interface Value {

    val expires: Instant   // Default value is `FOREVER`, means forever.

    companion object {
        val FOREVER: Instant = Instant.EPOCH
    }

    /**
     * Judge if the value is expired.
     */
    fun isExpired(): Boolean {
        return expires != Instant.EPOCH && expires.isBefore(Instant.now())
    }

    /**
     * Judge if the value is temporary, don't care if it has expired.
     */
    fun isTemporary(): Boolean {
        return expires.isAfter(Instant.EPOCH)
    }

}