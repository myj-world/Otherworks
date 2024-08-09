package screens.resources

import androidx.compose.runtime.Composable
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

actual suspend fun downloadFile(url: String): Boolean {
    return try {
        val response = HttpClient().get(url) {
            onDownload { bytesSentTotal, contentLength ->
                println("Downloaded $bytesSentTotal of $contentLength")
            }
        }.bodyAsChannel().toByteArray()

        val downloadsDir = System.getProperty("user.home") + "/Downloads"
        val file = File(downloadsDir, "file_${System.currentTimeMillis()}.pdf")
        file.parentFile?.mkdirs()
        withContext(Dispatchers.IO) {
            FileOutputStream(file).use { output ->
                output.write(response)
            }
        }
        true
    } catch (e: Exception) {
        false
    }
}

@Composable
actual fun openFile(url: String): Boolean {
    return true
}