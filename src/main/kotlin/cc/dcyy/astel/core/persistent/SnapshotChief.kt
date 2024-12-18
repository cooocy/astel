package cc.dcyy.astel.core.persistent

import cc.dcyy.astel.core.entry.Astel
import cc.dcyy.astel.SnapshotNotFoundException
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

object SnapshotChief {

    private val kryo = Kryo()
    private val L = KotlinLogging.logger {}

    init {
        kryo.isRegistrationRequired = false
    }

    /**
     * Serialize the Astel to local file system.
     */
    fun serialize(filePath: Path) {
        val begin = Instant.now()
        Files.createDirectories(filePath.parent);
        if (Files.exists(filePath)) {
            Files.delete(filePath)
        }
        Files.createFile(filePath)
        try {
            FileOutputStream(filePath.toFile()).use { fos ->
                Output(fos).use { output ->
                    kryo.writeObject(output, Astel.tbl)
                }
            }
            val end = Instant.now()
            val const = Duration.between(begin, end).toMillis()
            L.info { "Serialize OK. Const: $const ms, File: $filePath" }
        } catch (e: Exception) {
            L.error { e }
        }
    }

    /**
     * Deserialize from local file system and then fill into Astel.
     * The original data in Astel will be removed.
     */
    fun deserialize(filePath: Path) {
        val begin = Instant.now()
        if (!Files.exists(filePath)) {
            throw SnapshotNotFoundException("Snapshot file does not exist: $filePath")
        }
        try {
            FileInputStream(filePath.toFile()).use { fis ->
                Input(fis).use { input ->
                    val tbl = kryo.readObject(input, Astel.tbl::class.java)
                    Astel.clearAndFill(tbl)
                }
            }
            val end = Instant.now()
            val const = Duration.between(begin, end).toMillis()
            L.info { "Deserialize OK. Const: $const ms, File: $filePath" }
        } catch (e: Exception) {
            L.error { e }
        }
    }

}