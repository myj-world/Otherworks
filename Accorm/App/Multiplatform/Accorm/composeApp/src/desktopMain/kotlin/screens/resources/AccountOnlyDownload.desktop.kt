package screens.resources

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.apache.pdfbox.Loader
import org.apache.pdfbox.rendering.PDFRenderer
import org.jetbrains.skiko.toImage
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

actual object FileManager {
    actual suspend fun downloadToAppStorage(link: String): List<String> {
        return try {
            val response = HttpClient().get(link) {
                onDownload { bytesSentTotal, contentLength ->
                    println("Downloaded $bytesSentTotal of $contentLength")
                }
            }.bodyAsChannel().toByteArray()

            val response1 = response.copyOfRange(0, response.size / 2)
            val response2 = response.copyOfRange(response.size / 2, response.size)

            val downloadsDir = System.getProperty("user.dir") + "/Accorm/AppData"
            val file1 = File(downloadsDir, "file1${System.currentTimeMillis()}.pdf")
            val file2 = File(downloadsDir, "file2${System.currentTimeMillis()}.pdf")
            file1.parentFile?.mkdirs()
            file2.parentFile?.mkdirs()
            withContext(Dispatchers.IO) {
                FileOutputStream(file1).use { output ->
                    output.write(response1)
                }
                FileOutputStream(file2).use { output ->
                    output.write(response2)
                }
            }
            listOf(file1.absolutePath, file2.absolutePath)
        } catch (e: Exception) {
            emptyList()
        }
    }

    actual suspend fun loadFileFromStorage(
        name: String,
        file1: String,
        file2: String
    ) {
        val file1Bytes = File(file1).readBytes()
        val file2Bytes = File(file2).readBytes()
        val tempFile = withContext(Dispatchers.IO) {
            File.createTempFile("Accorm"+name.filter { it.isLetterOrDigit() }, ".pdf")
        }
        tempFile.deleteOnExit()
        val outputStream = withContext(Dispatchers.IO) {
            tempFile.outputStream()
        }
        withContext(Dispatchers.IO) {
            outputStream.write(file1Bytes + file2Bytes)
            outputStream.close()
        }

//        val pdf = Loader.loadPDF(tempFile)
//        val pdfRenderer = PDFRenderer(pdf)
//        val imgs = mutableListOf<BitmapPainter>()
//        for (i in 0 until pdf.numberOfPages) {
//            val image = pdfRenderer.renderImage(i)
//            imgs.add(BitmapPainter(image.toImage().toComposeImageBitmap()))
//        }
        if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            withContext(Dispatchers.IO) {
                Desktop.getDesktop().open(tempFile)
            }
        } else {
            println("Desktop not supported")
        }

        delay(5000)
    }

    actual suspend fun deleteFileFromStorage(file1: String, file2: String) {
        File(file1).delete()
        File(file2).delete()
    }
}