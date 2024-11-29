package cc.dcyy.astel.command

import cc.dcyy.astel.Astel
import cc.dcyy.astel.CommandErrorException
import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Listl
import cc.dcyy.astel.entry.Strings
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class CommandExecutorTest {

    @Test
    fun testExecuteUnknownCommand() {
        val i1 = "foo"
        val r1 = assertThrows<CommandErrorException> { CommandExecutor.execute(i1) }
        assertEquals(Message.UNKNOWN_CMD, r1.message)

        val i2 = "foo ok"
        val r2 = assertThrows<CommandErrorException> { CommandExecutor.execute(i2) }
        assertEquals(Message.UNKNOWN_CMD, r2.message)
    }

    @Test
    fun testExecuteDoSet() {
        val i1 = "set name"
        val r1 = CommandExecutor.execute(i1)
        assertEquals(Message.ARGS_NUM_WRONG, r1.value)
        val i2 = "set name cooocy cy"
        val r2 = CommandExecutor.execute(i2)
        assertEquals(Message.ARGS_NUM_WRONG, r2.value)

        val i3 = "set name cooocy"
        val r3 = CommandExecutor.execute(i3)
        assertEquals(0, r3.code)
    }

    @Test
    fun testExecuteDoGet() {
        Astel.clear()
        Astel.put(Key.new("name"), Strings.new("cooocy"))
        val i2 = "get name"
        val r2 = CommandExecutor.execute(i2)
        assertEquals("cooocy", r2.value)

        val i3 = "get name2"
        val r3 = CommandExecutor.execute(i3)
        assertEquals("", r3.value)

        Astel.put(Key.new("hobby"), Listl.new("coding", "running"))
        val i4 = "get hobby"
        val r4 = CommandExecutor.execute(i4)
        assertEquals(Message.KEY_NOT_STRINGS, r4.value)

        val i5 = "get"
        val r5 = CommandExecutor.execute(i5)
        assertEquals(Message.ARGS_NUM_WRONG, r5.value)

        val i6 = "get not_exists"
        val r6 = CommandExecutor.execute(i6)
        assertEquals("", r6.value)
    }

}