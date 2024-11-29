package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.TemporalUnit

/**
 * A String value.
 */
class Strings private constructor() : Value() {

    var value: String = ""
        private set

    companion object {
        /**
         * New a forever value.
         */
        fun new(v: String): Strings {
            val strings = Strings()
            strings.value = v
            return strings
        }

        /**
         * New a temporary value.
         */
        fun new(v: String, expire: Long, unit: TemporalUnit): Strings {
            val strings = Strings()
            strings.value = v
            strings.expires = Instant.now().plus(expire, unit)
            return strings
        }
    }

}