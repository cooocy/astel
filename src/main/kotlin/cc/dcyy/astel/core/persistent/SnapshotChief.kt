package cc.dcyy.astel.core.persistent

import cc.dcyy.astel.DeserializeFromSnapshotException
import cc.dcyy.astel.SerializeToSnapshotException
import cc.dcyy.astel.core.entry.Astel
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.Duration
import mu.KotlinLogging
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object SnapshotChief {

    private val kryo = Kryo()
    private val L = KotlinLogging.logger {}

    init {
        kryo.isRegistrationRequired = false
    }

    /**
     * Serialize the Astel to local file system.
     * @param snapshotDir The directory to store the snapshot files. e.g. persistent/snapshots
     * @return The path of the snapshot file.
     */
    fun serialize(snapshotDir: Path): Path {
        // If the snapshot directory does not exist, create it.
        // No exception will be thrown if the directory already exists.
        Files.createDirectories(snapshotDir)
        val snapshotFilePath = Path.of(snapshotDir.toString(), generateSnapshotFileName())
        val begin = Instant.now()
        if (Files.exists(snapshotFilePath)) {
            Files.delete(snapshotFilePath)
        }
        Files.createFile(snapshotFilePath)
        try {
            FileOutputStream(snapshotFilePath.toFile()).use { fos ->
                Output(fos).use { output ->
                    kryo.writeObject(output, Astel.tbl)
                }
            }
            val end = Instant.now()
            val const = Duration.between(begin, end).toMillis()
            L.info { "Serialize OK. Const: $const ms, File: $snapshotFilePath" }
            return snapshotFilePath
        } catch (e: Exception) {
            L.error { e }
            throw SerializeToSnapshotException()
        }
    }

    /**
     * Deserialize from local file system and then fill into Astel.
     * The original data in Astel will be removed.
     * If there is no snapshot files or the directory is not exists, nothing will be done;
     * else the latest snapshot file will be used.
     *
     * @param snapshotDir The directory of the snapshot files. e.g. persistent/snapshots
     * @return The path of the snapshot file be chosen.
     */
    fun deserialize(snapshotDir: Path): Path? {
        val latestSnapshotFilePath = findLatestSnapshot(snapshotDir)
        if (latestSnapshotFilePath == null) {
            L.info { "No snapshots found in the directory: $snapshotDir" }
            return null
        }
        val begin = Instant.now()
        try {
            FileInputStream(latestSnapshotFilePath.toFile()).use { fis ->
                Input(fis).use { input ->
                    val tbl = kryo.readObject(input, Astel.tbl::class.java)
                    Astel.clearAndFill(tbl)
                }
            }
            val end = Instant.now()
            val const = Duration.between(begin, end).toMillis()
            L.info { "Deserialize OK. Const: $const ms, From file: $latestSnapshotFilePath" }
            return latestSnapshotFilePath
        } catch (e: Exception) {
            L.error { e }
            throw DeserializeFromSnapshotException()
        }
    }

    /**
     * Clean expired snapshots in the snapshot directory，the threshold is the number of snapshots to keep.
     */
    fun cleanExpiredSnapshots(snapshotDir: Path, threshold: Int) {
        if (!Files.exists(snapshotDir)) {
            return
        }
        val snapshotFiles = Files.list(snapshotDir).filter { it.fileName.toString().endsWith(".sn") }.sorted { o1, o2 -> o1.fileName.toString().compareTo(o2.fileName.toString()) }.toList()
        if (snapshotFiles.size <= threshold) {
            return
        }
        val toDelete = snapshotFiles.subList(0, snapshotFiles.size - threshold)
        toDelete.forEach {
            try {
                Files.delete(it)
            } catch (e: Exception) {
                L.error { e }
            }
        }
        L.info { "Clean expired snapshots. Threshold: $threshold, Deleted: ${toDelete.size}" }
    }

    /**
     * Find the latest snapshot file in the snapshot directory.
     */
    private fun findLatestSnapshot(snapshotDir: Path): Path? {
        if (!Files.exists(snapshotDir)) {
            return null
        }
        return Files.list(snapshotDir).filter { it.fileName.toString().endsWith(".sn") }.max { o1, o2 -> o1.fileName.toString().compareTo(o2.fileName.toString()) }.orElse(null)
    }

    /**
     * Generate a snapshot file name. e.g. 20241219132642.sn
     */
    private fun generateSnapshotFileName(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return Instant.now().atZone(ZoneId.systemDefault()).format(formatter) + ".sn"
    }

}