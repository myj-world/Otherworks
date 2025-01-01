package screens.resources

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

@SuppressLint("StaticFieldLeak")
actual object FileManager {
    var context: Context? = null

    fun initContext(context: Context) {
        this.context = context
    }

    actual suspend fun downloadToAppStorage(link: String): List<String> {
        return try {
            var response: ByteArray? = null
            withContext(Dispatchers.IO) {
                response = OkHttpClient().newCall(Request.Builder().url(link).build()).execute().body()?.bytes()
            }
            delay(5000)
            val response1 = response?.copyOfRange(0, response!!.size / 2)
            val response2 = response?.copyOfRange(response!!.size / 2, response!!.size)

            val dir = context?.applicationInfo?.dataDir

            val file1 = File(dir, "file1${System.currentTimeMillis()}.pdf")
            delay(5)
            val file2 = File(dir, "file2${System.currentTimeMillis()}.pdf")
            file1.parentFile?.mkdirs()
            file2.parentFile?.mkdirs()

            withContext(Dispatchers.IO) {
                file1.writeBytes(response1!!)
                file2.writeBytes(response2!!)
            }

            listOf(file1.absolutePath, file2.absolutePath)
        } catch (e: Exception) {
            println(e)
            emptyList()
        }
    }

    actual suspend fun loadFileFromStorage(
        name: String,
        file1: String,
        file2: String
    ) : List<BitmapPainter> {
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

        val pdfUri = FileProvider.getUriForFile(context!!, "com.yousufjamil.accorm.provider", tempFile)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context?.startActivity(intent)
        return emptyList()
    }

    actual suspend fun deleteFileFromStorage(file1: String, file2: String) {
        File(file1).delete()
        File(file2).delete()
    }
}