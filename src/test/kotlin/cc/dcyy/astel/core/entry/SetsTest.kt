package cc.dcyy.astel.core.entry

import org.junit.jupiter.api.Assertions.*
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class SetsTest {

    @Test
    fun testNew() {
        val s1 = Sets.Companion.new()
        assertEquals(0, s1.size())

        val s2 = Sets.Companion.new(10, ChronoUnit.MICROS, 1, 2, 3, "Four")
        assertEquals(4, s2.size())
    }

    @Test
    fun testContains() {
        val s1 = Sets.Companion.new("Hello")
        assertTrue(s1.contains("Hello"))
        assertFalse(s1.contains("HELLO"))
    }

    @Test
    fun testRemove() {
        val s1 = Sets.Companion.new("Hello")
        assertTrue(s1.contains("Hello"))
        s1.remove("Hello")
        s1.remove("World")
        assertFalse(s1.contains("Hello"))
    }

    @Test
    fun testIntersect() {
        val s1 = Sets.Companion.new("One", "Two", 3, "Four")
        val s2 = Sets.Companion.new(3, "Four", "Five", "Six")
        var intersected1 = s1.intersect(s2)
        var intersected2 = s2.intersect(s1)
        assertEquals(intersected1, intersected2)
        assertEquals(setOf<Any>(3, "Four"), intersected1)
    }

    @Test
    fun testUnion() {
        val s1 = Sets.Companion.new("One", "Two", 3, "Four")
        val s2 = Sets.Companion.new(3, "Four", "Five", "Six")
        var u1 = s1.union(s2)
        var u2 = s2.union(s1)
        assertEquals(u1, u2)
        assertEquals(setOf<Any>(3, "Four", "Five", "Six", "One", "Two"), u1)
    }

}