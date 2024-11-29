package cc.dcyy.astel.entry

import org.junit.jupiter.api.Assertions.*
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class ListlTest {

    @Test
    fun testNew() {
        val l1 = Listl.new()
        assertEquals(0, l1.size())

        val l2 = Listl.new(10, ChronoUnit.MICROS, 1, 2, 3, "Four")
        assertEquals(4, l2.size())
    }

    @Test
    fun testGet() {
        val l = Listl.new(1, 2, 3, "Four")
        assertEquals(1, l.get(0))
        assertEquals("Four", l.get(3))
    }

    @Test
    fun testRemoveAt() {
        val l = Listl.new(1, 2, 3, "Four")
        l.removeAt(2)
        val expected = listOf<Any>(1, 2, "Four")
        for (i in 0 until expected.size) {
            assertEquals(expected[i], l.get(i))
        }
    }

    @Test
    fun testLeftPush() {
        val l = Listl.new(1, 2, 3, "Four")
        val e = "I'm the first."
        l.leftPush(e)
        assertEquals(e, l.get(0))
    }

    @Test
    fun testLeftPop() {
        val l = Listl.new(1, 2, 3, "Four")
        assertEquals(1, l.leftPop())
        assertEquals(2, l.leftPop())
        assertEquals(3, l.leftPop())
        assertEquals("Four", l.leftPop())
        assertEquals(null, l.leftPop())
    }

    @Test
    fun testRightPush() {
        val l = Listl.new(1, 2, 3, "Four")
        val e = "I'm the last."
        l.rightPush(e)
        assertEquals(e, l.get(l.size() - 1))
    }

    @Test
    fun testRightPop() {
        val l = Listl.new(1, 2, 3, "Four")
        assertEquals("Four", l.rightPop())
        assertEquals(3, l.rightPop())
        assertEquals(2, l.rightPop())
        assertEquals(1, l.rightPop())
        assertEquals(null, l.rightPop())
    }

    @Test
    fun testRange() {
        val l1 = Listl.new(1, 2, 3, "Four", "Five", "Six", "Seven")
        val actual = l1.range(3, 6)
        val expected = listOf<Any>("Four", "Five", "Six")
        assertEquals(expected, actual)
    }

}