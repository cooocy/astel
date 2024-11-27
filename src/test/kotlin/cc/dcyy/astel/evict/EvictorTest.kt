package cc.dcyy.astel.evict

import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.Astel
import cc.dcyy.astel.entry.Strings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit

class EvictorTest {

    @Test
    fun testRandomEvict() {
        Astel.clear()
        for (i in 0 until 10) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val size = Astel.size()
        Evictor.randomEvict()
        assertTrue(Astel.size() < size)
    }

    @Test
    fun testRandomEvictL0() {
        Astel.clear()
        for (i in 0 until 4000) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Astel.size()
        Evictor.randomEvict()
        val currSize = Astel.size()
        assertEquals(4000, originalSize)
        assertEquals(2000, currSize)
    }

    @Test
    fun testRandomEvictL1() {
        Astel.clear()
        for (i in 0 until 9000) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Astel.size()
        Evictor.randomEvict()
        val currSize = Astel.size()
        assertEquals(9000, originalSize)
        assertEquals(6000, currSize)
    }

    @Test
    fun testRandomEvictL2() {
        Astel.clear()
        for (i in 0 until 20000) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Astel.size()
        Evictor.randomEvict()
        val currSize = Astel.size()
        assertEquals(20000, originalSize)
        assertEquals(18000, currSize)
    }

    @Test
    fun testRandomEvictL3() {
        Astel.clear()
        for (i in 0 until 1010000) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        val originalSize = Astel.size()
        Evictor.randomEvict()
        val currSize = Astel.size()
        assertEquals(1010000, originalSize)
        assertEquals(1010000 - 10100, currSize)
    }

}