package cc.dcyy.astel.persistent.snapshot

import cc.dcyy.astel.Astel
import cc.dcyy.astel.entry.Key
import cc.dcyy.astel.entry.Strings
import cc.dcyy.astel.persistent.deserialize
import cc.dcyy.astel.persistent.serialize
import org.junit.jupiter.api.Assertions.*
import java.nio.file.Files
import java.nio.file.Paths
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class SnapshotSerializerTest {

    @Test
    fun testSerialize() {
        val filePath = Paths.get("ss/00.sn")
        Astel.put(Key.new("k1"), Strings.new("你好世界 1", 1, ChronoUnit.DAYS))
        Astel.put(Key.new("k2"), Strings.new("你好世界 2", 2, ChronoUnit.DAYS))
        Astel.put(Key.new("k3"), Strings.new("Hello World 3"))
        serialize(filePath)
        assertTrue(Files.exists(filePath))
        Files.delete(filePath)
        Files.delete(filePath.parent)
    }

    @Test
    fun testDeserialize() {
        Astel.clear()
        val filePath = Paths.get("ss/00.sn")
        val k1 = Key.new("k1")
        val k2 = Key.new("k2")
        val v1 = Strings.new("你好世界 1", 1, ChronoUnit.DAYS)
        val v2 = Strings.new("你好世界 2", 1, ChronoUnit.DAYS)
        Astel.put(k1, v1)
        Astel.put(k2, v2)
        serialize(filePath)

        Astel.clear()
        deserialize(filePath)
        assertEquals(2, Astel.size())

        assertTrue(Files.exists(filePath))
        Files.delete(filePath)
        Files.delete(filePath.parent)
    }

}