package cc.dcyy.astel.core.entry

import java.time.Instant

abstract class Value {
    private val forever: Instant = Instant.EPOCH

    var expires: Instant = forever // Default value is `forever`, means forever.

    /**
     * Judge if the value is expired.
     */
    fun isExpired(): Boolean {
        return expires != forever && expires.isBefore(Instant.now())
    }

    /**
     * Judge if the value is temporary, don't care if it has expired.
     */
    fun isTemporary(): Boolean {
        return expires.isAfter(forever)
    }

}