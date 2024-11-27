package cc.dcyy.astel.persistent

import cc.dcyy.astel.Astel
import cc.dcyy.astel.SnapshotNotFoundException
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path

private val kryo = Kryo()

// Only in this way can the following initialization code be executed.
val neverRemove = run {
    kryo.isRegistrationRequired = false
}

/**
 * Serialize the Astel to local file system.
 */
fun serialize(filePath: Path) {
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
    } catch (e: Exception) {
        // TODO logger
        e.printStackTrace()
    }
}

/**
 * Deserialize from local file system and then fill into Astel.
 * The original data in Astel will be removed.
 */
fun deserialize(filePath: Path) {
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
    } catch (e: Exception) {
        e.printStackTrace()
    }
}