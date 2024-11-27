package cc.dcyy.astel

import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Strings
import org.junit.jupiter.api.Assertions.*
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class AstelTest {

    @Test
    fun testPut() {
        Astel.clear()

        val k = Key.new("k")
        val v1 = Strings.new("v1")
        Astel.put(k, v1)
        assertEquals(v1, Astel.get(k))

        val v2 = Strings.new("v2")
        Astel.put(k, v2)
        assertEquals(v2, Astel.get(k))
    }

    @Test
    fun testRemove() {
        Astel.clear()
        val k = Key.new("k")
        val v1 = Strings.new("v1")
        Astel.put(k, v1)
        Astel.remove(k)
        assertFalse(Astel.contains(k))
        assertNull(Astel.get(k))

        val v2 = Strings.new("v1", -100, ChronoUnit.MICROS)
        Astel.put(k, v2)
        Astel.remove(k)
        assertFalse(Astel.contains(k))
        assertNull(Astel.get(k))
    }

    @Test
    fun testContains() {
        Astel.clear()
        val k = Key.new("k")
        Astel.put(k, Strings.new("v"))
        assertTrue(Astel.contains(k))
        assertFalse(Astel.contains(Key.new("-key100")))
    }

    @Test
    fun testSize() {
        Astel.clear()
        assertEquals(0, Astel.size())
        Astel.put(Key.new("k1"), Strings.new("v1"))
        assertEquals(1, Astel.size())
    }


    /**
     * Test whether a removing occurs when an expired key is got.
     */
    @Test
    fun testRemovedWhenGot() {
        Astel.clear()
        Astel.put(Key.new("k"), Strings.new("v", -1, ChronoUnit.MICROS))
        assertEquals(1, Astel.size())
        val value = Astel.get(Key.new("k"))
        assertNull(value)
        assertEquals(0, Astel.size())
    }

    @Test
    fun testClearAndFill() {
        Astel.clear()
        Astel.put(Key.new("k1"), Strings.new("v1"))
        Astel.put(Key.new("k2"), Strings.new("v2"))
        Astel.clearAndFill(Astel.tbl.copyOf())
        assertEquals(2, Astel.size())

        val ex = assertThrows(AstelFillException::class.java) { Astel.clearAndFill(arrayOf()) }
    }

}