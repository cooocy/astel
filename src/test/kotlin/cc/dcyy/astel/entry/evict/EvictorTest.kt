package cc.dcyy.astel.entry.evict

import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Root
import cc.dcyy.astel.entry.Strings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit

class EvictorTest {

    @Test
    fun testRandomEvict() {
        Root.clear()
        for (i in 0 until 10) {
            Root.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val size = Root.size()
        Evictor.randomEvict()
        assertTrue(Root.size() < size)
    }

    @Test
    fun testRandomEvictL0() {
        Root.clear()
        for (i in 0 until 4000) {
            Root.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Root.size()
        Evictor.randomEvict()
        val currSize = Root.size()
        assertEquals(4000, originalSize)
        assertEquals(2000, currSize)
    }

    @Test
    fun testRandomEvictL1() {
        Root.clear()
        for (i in 0 until 9000) {
            Root.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Root.size()
        Evictor.randomEvict()
        val currSize = Root.size()
        assertEquals(9000, originalSize)
        assertEquals(6000, currSize)
    }

    @Test
    fun testRandomEvictL2() {
        Root.clear()
        for (i in 0 until 20000) {
            Root.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Root.size()
        Evictor.randomEvict()
        val currSize = Root.size()
        assertEquals(20000, originalSize)
        assertEquals(18000, currSize)
    }

    @Test
    fun testRandomEvictL3() {
        Root.clear()
        for (i in 0 until 1010000) {
            Root.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Root.size()
        Evictor.randomEvict()
        val currSize = Root.size()
        assertEquals(1010000, originalSize)
        assertEquals(1010000 - 10100, currSize)
    }

}