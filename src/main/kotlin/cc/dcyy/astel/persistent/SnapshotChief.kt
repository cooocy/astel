package cc.dcyy.astel.persistent

import cc.dcyy.astel.Astel
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Output
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path

private val kryo = Kryo()

// Only in this way can the following initialization code be executed.
val neverRemove = run {
    kryo.isRegistrationRequired = false
}

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

fun deserialize(filePath: Path) {

}