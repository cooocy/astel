package cc.dcyy.astel.core.entrance

import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.CommandArgsErrException
import cc.dcyy.astel.OK
import cc.dcyy.astel.UnknownCommandException
import cc.dcyy.astel.ValueTypeNotMatchException
import cc.dcyy.astel.core.entry.Key
import cc.dcyy.astel.core.entry.Listl
import cc.dcyy.astel.core.entry.Strings
import org.junit.jupiter.api.Assertions.*
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandExecutorTest {

    @Test
    fun testExecuteUnknownCommand() {
        val r1 = CommandExecutor.execute("foo")
        var e1 = UnknownCommandException()
        assertEquals(e1.code, r1.code)
        assertEquals(e1.message, r1.content)

        val r2 = CommandExecutor.execute("foo ok")
        var e2 = UnknownCommandException()
        assertEquals(e2.code, r2.code)
        assertEquals(e2.message, r2.content)
    }

    @Test
    fun testExecuteDoSet() {
        val r1 = CommandExecutor.execute("set name")
        var e1 = CommandArgsErrException()
        assertEquals(e1.code, r1.code)
        assertEquals(e1.message, r1.content)

        val r2 = CommandExecutor.execute("set name cooocy cy")
        var e2 = CommandArgsErrException()
        assertEquals(e2.code, r2.code)
        assertEquals(e2.message, r2.content)

        val r3 = CommandExecutor.execute("set name cooocy")
        assertEquals(0, r3.code)
    }

    @Test
    fun testExecuteDoGet() {
        Astel.clear()
        Astel.put(Key.new("name"), Strings.new("cooocy"))
        val r2 = CommandExecutor.execute("get name")
        assertEquals("cooocy", r2.content)

        val r3 = CommandExecutor.execute("get name2")
        assertEquals("", r3.content)

        Astel.put(Key.new("hobby"), Listl.new("coding", "running"))


        val r4 = CommandExecutor.execute("get hobby")
        var e4 = ValueTypeNotMatchException()
        assertEquals(e4.code, r4.code)
        assertEquals(e4.message, r4.content)

        val r5 = CommandExecutor.execute("get")
        var e5 = CommandArgsErrException()
        assertEquals(e5.code, r5.code)
        assertEquals(e5.message, r5.content)

        val r = CommandExecutor.execute("get not_exists")
        assertEquals("", r.content)
    }

    @Test
    fun testExecuteDoDel() {
        Astel.clear()
        val r2 = CommandExecutor.execute("del name")
        assertEquals(OK, r2.code)

        CommandExecutor.execute("set name cooocy")
        assertTrue(Astel.contains(Key.new("name")))
        val r4 = CommandExecutor.execute("del name")
        assertEquals(OK, r4.code)
        assertFalse(Astel.contains(Key.new("name")))
    }

    @Test
    fun testDoExists() {
        Astel.clear()
        val r2 = CommandExecutor.execute("exists name")
        assertEquals(OK, r2.code)
        assertEquals(false, r2.content)

        Astel.put(Key.new("name"), Strings.new("cooocy"))
        val r3 = CommandExecutor.execute("exists name")
        assertEquals(OK, r3.code)
        assertEquals(true, r3.content)
    }

    @Test
    fun testDoExpire() {
        Astel.clear()
        Astel.put(Key.new("name"), Strings.new("cooocy"))
        assertFalse(Astel.get(Key.new("name"))!!.isTemporary())
        val curr = Instant.now()
        val r1 = CommandExecutor.execute("expire name 10")
        assertTrue(Astel.get(Key.new("name"))!!.isTemporary())
        val v = Astel.get(Key.new("name"))
        assertTrue(v!!.expires.isAfter(curr))
        val seconds = Duration.between(curr, v.expires).get(ChronoUnit.SECONDS)
        assertEquals(10, seconds)
    }

    @Test
    fun testDoTtl() {
        Astel.clear()
        val key = Key.new("hobby")
        Astel.put(key, Strings.new("Kotlin"))
        assertEquals(-1L, CommandExecutor.execute("ttl hobby").content)

        assertEquals(-0L, CommandExecutor.execute("ttl hobby2").content)

        Astel.put(key, Listl.new(10, ChronoUnit.SECONDS, "Kotlin", "Java"))
        val ttl = CommandExecutor.execute("ttl hobby").content as Long
        assertTrue(ttl > 0)
        assertTrue(ttl <= 10)
    }

}