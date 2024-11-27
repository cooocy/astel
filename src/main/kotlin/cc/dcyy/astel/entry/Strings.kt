package cc.dcyy.astel.entry

import java.time.Instant
import java.time.temporal.TemporalUnit

class Strings private constructor() : Value {
    override var expires: Instant = Value.FOREVER
        private set
    var value: String = ""
        private set

    companion object {
        /**
         * New a forever value.
         * TODO Remove method overload, use default args.
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