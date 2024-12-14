package cc.dcyy.astel.entrance

import cc.dcyy.astel.Astel
import cc.dcyy.astel.CommandArgsErrException
import cc.dcyy.astel.UnknownCommandException
import cc.dcyy.astel.ValueTypeNotMatchException
import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Listl
import cc.dcyy.astel.entry.Strings
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

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

}