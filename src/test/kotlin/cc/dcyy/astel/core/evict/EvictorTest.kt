package cc.dcyy.astel.core.evict

import cc.dcyy.astel.core.entry.Key
import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.core.entry.Strings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit
import kotlin.math.abs

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
        assertTrue(abs(currSize - 6000) < 5)
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

    @Test
    fun testFullEvictL() {
        Astel.clear()
        for (i in 0 until 100) {
            Astel.put(Key.new("k$i"), Strings.new("v$i", -100, ChronoUnit.MICROS))
        }
        for (i in 0 until 10) {
            Astel.put(Key.new("nk$i"), Strings.new("v$i"))
        }
        assertEquals(110, Astel.size())
        Evictor.fullEvict()
        assertEquals(10, Astel.size())
    }

}