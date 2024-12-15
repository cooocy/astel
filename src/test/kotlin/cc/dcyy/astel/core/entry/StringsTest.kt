package cc.dcyy.astel.core.entry

import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class StringsTest {

    @Test
    fun testNew() {
        val v1 = Strings.Companion.new("v")
        val v2 = Strings.Companion.new("v2", 20, ChronoUnit.HOURS)
        assertTrue(v2.expires.isAfter(Instant.now()))
    }

    @Test
    fun testIsTemporaryAndIsExpired() {
        val v1 = Strings.Companion.new("v1")
        assertFalse(v1.isTemporary())
        assertFalse(v1.isExpired())

        val v2 = Strings.Companion.new("v2", -9, ChronoUnit.MINUTES)
        assertTrue(v2.isTemporary())
        assertTrue(v2.isExpired())
    }

}