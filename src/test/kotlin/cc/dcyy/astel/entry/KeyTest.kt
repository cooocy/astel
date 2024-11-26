package cc.dcyy.astel.entry

import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class KeyTest {

    @Test
    fun testNew() {
        assertThrows(IllegalArgumentException::class.java) { Key.new("") }
        val k1 = Key.new("k1")
        assertTrue(k1.key.isNotBlank())
        assertTrue(k1.hash > 0)
        assertEquals(Instant.EPOCH, k1.expires)

        val k2 = Key.new("k2", 20, ChronoUnit.HOURS)
        assertTrue(k2.expires.isAfter(Instant.now()))
    }

    @Test
    fun testEqualsAndHash() {
        val k1 = Key.new("k1")
        val k2 = Key.new("k1", 20, ChronoUnit.HOURS)
        assertEquals(k1, k2)
        assertEquals(k1.hash, k2.hash)

        val k3 = Key.new("k3")
        assertNotEquals(k1, k3)
        assertNotEquals(k2, k3)
    }

}

