package cc.dcyy.astel.core.persistent

import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.core.entry.Key
import cc.dcyy.astel.core.entry.Strings
import org.junit.jupiter.api.Assertions.*
import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.Paths
import java.time.temporal.ChronoUnit
import kotlin.test.Test

class SnapshotChiefTest {

    @Test
    fun testSerialize() {
        val snapshotDir = Paths.get("persistent/snapshots")
        Astel.put(Key.new("k1"), Strings.new("你好世界 1", 1, ChronoUnit.DAYS))
        Astel.put(Key.new("k2"), Strings.new("你好世界 2", 2, ChronoUnit.DAYS))
        Astel.put(Key.new("k3"), Strings.new("Hello World 3"))
        val snapshotFilePath = SnapshotChief.serialize(snapshotDir)
        assertTrue(Files.exists(snapshotFilePath))
        Files.delete(snapshotFilePath)
    }

    @Test
    fun testDeserialize() {
        Astel.clear()
        val snapshotDir = Paths.get("persistent/snapshots")
        val k1 = Key.new("k1")
        val k2 = Key.new("k2")
        val v1 = Strings.new("你好世界 1", 1, ChronoUnit.DAYS)
        val v2 = Strings.new("你好世界 2", 1, ChronoUnit.DAYS)
        Astel.put(k1, v1)
        Astel.put(k2, v2)
        val p1 = SnapshotChief.serialize(snapshotDir)

        Astel.clear()
        val p2 = SnapshotChief.deserialize(snapshotDir)
        assertEquals(2, Astel.size())
        assertEquals(p1, p2)

        Files.delete(p1)
    }

    @Test
    fun testCleanExpiredSnapshots() {
        deleteAllSnapshots()
        batchWriteSnapshots(10)
        SnapshotChief.cleanExpiredSnapshots(Paths.get("persistent/snapshots"), 3)
        assertEquals(3, Files.list(Paths.get("persistent/snapshots")).count())
    }

    @Test
    fun testGenerateSnapshotFileName() {
        val fileName = invokeGenerateSnapshotFileName()
        assertTrue(fileName.endsWith(".sn"))
    }

    private fun invokeGenerateSnapshotFileName(): String {
        val method: Method = SnapshotChief::class.java.getDeclaredMethod("generateSnapshotFileName")
        method.isAccessible = true
        return method.invoke(SnapshotChief) as String
    }

    private fun batchWriteSnapshots(num: Int) {
        val snapshotDir = Paths.get("persistent/snapshots")
        for (i in 1..num) {
            Astel.put(Key.new("k$i"), Strings.new("你好世界 $i", 1, ChronoUnit.DAYS))
            SnapshotChief.serialize(snapshotDir)
            Thread.sleep(1000)
        }
    }

    private fun deleteAllSnapshots() {
        val snapshotDir = Paths.get("persistent/snapshots")
        if (Files.exists(snapshotDir)) {
            Files.list(snapshotDir).forEach { Files.delete(it) }
        }
    }

}