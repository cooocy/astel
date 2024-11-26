package cc.dcyy.astel.entry

import org.junit.jupiter.api.Assertions.*
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class RootTest {

    @Test
    fun testPut() {
        Root.clear()

        val k = Key.new("k")
        val v1 = Strings.new("v1")
        Root.put(k, v1)
        assertEquals(v1, Root.get(k))

        val v2 = Strings.new("v2")
        Root.put(k, v2)
        assertEquals(v2, Root.get(k))
    }

    @Test
    fun testRemove() {
        Root.clear()
        val k = Key.new("k")
        val v1 = Strings.new("v1")
        Root.put(k, v1)
        Root.remove(k)
        assertFalse(Root.contains(k))
        assertNull(Root.get(k))

        val v2 = Strings.new("v1", -100, ChronoUnit.MICROS)
        Root.put(k, v2)
        Root.remove(k)
        assertFalse(Root.contains(k))
        assertNull(Root.get(k))
    }

    @Test
    fun testContains() {
        Root.clear()
        val k = Key.new("k")
        Root.put(k, Strings.new("v"))
        assertTrue(Root.contains(k))
        assertFalse(Root.contains(Key.new("-key100")))
    }

    @Test
    fun testSize() {
        Root.clear()
        assertEquals(0, Root.size())
        Root.put(Key.new("k1"), Strings.new("v1"))
        assertEquals(1, Root.size())
    }


    /**
     * Test whether a removing occurs when an expired key is got.
     */
    @Test
    fun testRemovedWhenGot() {
        Root.clear()
        Root.put(Key.new("k"), Strings.new("v", -1, ChronoUnit.MICROS))
        assertEquals(1, Root.size())
        val value = Root.get(Key.new("k"))
        assertNull(value)
        assertEquals(0, Root.size())
    }

}