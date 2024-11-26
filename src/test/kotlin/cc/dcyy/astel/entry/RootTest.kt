package cc.dcyy.astel.entry

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class RootTest {

    @Test
    fun testPut() {
        val k = Key.new("k")

        assertNull(Root.get(k))

        val v1 = Strings("v1")
        Root.put(k, v1)
        assertEquals(v1, Root.get(k))

        val v2 = Strings("v2")
        Root.put(k, v2)
        assertEquals(v2, Root.get(k))
    }

    @Test
    fun testContains() {
        val k = Key.new("k")
        assertTrue(Root.contains(k))
        assertFalse(Root.contains(Key.new("-key100")))
    }

    @Test
    fun testSize() {
        assertEquals(1, Root.size())
    }

}