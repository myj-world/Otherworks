package screens.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import org.jetbrains.skiko.toImage
import java.io.File
import java.io.FileOutputStream

actual suspend fun downloadFile(title: String, url: String): Boolean {
    return try {
        val response = HttpClient().get(url) {
            onDownload { bytesSentTotal, contentLength ->
                println("Downloaded $bytesSentTotal of $contentLength")
            }
        }.bodyAsChannel().toByteArray()

        val downloadsDir = System.getProperty("user.home") + "/Downloads"
        val file = File(downloadsDir, "${title}_${System.currentTimeMillis()}.pdf")
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
actual fun openFile(title: String, url: String): Boolean {
    return true
}

actual suspend fun desktopLoad(url: String) : List<BitmapPainter> {
    val pdf: PDDocument
    val pdfRenderer: PDFRenderer
    val file: File
    val list = MutableList(0) { BitmapPainter(ImageBitmap(1, 1)) }
    try {
        val response = HttpClient().get(url) {
            onDownload { bytesSentTotal, contentLength ->
                println("Downloaded $bytesSentTotal of $contentLength")
            }
        }.bodyAsChannel().toByteArray()
        file = withContext(Dispatchers.IO) {
            File.createTempFile("file", ".pdf")
        }
        file.writeBytes(response)
        file.deleteOnExit()

        pdf = Loader.loadPDF(file)
        pdfRenderer = PDFRenderer(pdf)
    } catch (e: Exception) {
        return listOf()
    }

    for (i in 0 until pdf.numberOfPages) {
        val image = pdfRenderer.renderImage(i,2f)
        list.add(BitmapPainter(image.toImage().toComposeImageBitmap()))
    }
    list.removeAt(0)

    file.delete()

    return list
}

//actual fun getInvertedUrl(ppqUrl: String): String {
//    return if (ppqUrl.contains("qp")) {
//        ppqUrl.replace("qp", "ms")
//    } else {
//        ppqUrl.replace("ms", "qp")
//    }
//}