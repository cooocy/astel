package cc.dcyy.astel.core.entry

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class KeyTest {

    @Test
    fun testNew() {
        assertThrows(IllegalArgumentException::class.java) { Key.Companion.new("") }
        val k1 = Key.Companion.new("k1")
        assertTrue(k1.key.isNotBlank())
        assertTrue(k1.hash > 0)
    }

    @Test
    fun testEqualsAndHash() {
        val k1 = Key.Companion.new("k1")
        val k2 = Key.Companion.new("k1")
        assertEquals(k1, k2)
        assertEquals(k1.hash, k2.hash)

        val k3 = Key.Companion.new("k3")
        assertNotEquals(k1, k3)
        assertNotEquals(k2, k3)
    }

}

