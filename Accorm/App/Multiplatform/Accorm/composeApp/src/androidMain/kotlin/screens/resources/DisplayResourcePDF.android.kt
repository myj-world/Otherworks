package screens.resources

import android.content.Intent
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

actual suspend fun downloadFile(url: String): Boolean {
    return true
}

@Composable
actual fun openFile(url: String): Boolean {
    var display by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf(false)
    }

    val file = File.createTempFile("file", ".pdf")
    file.deleteOnExit()
    LaunchedEffect(true) {
        try {
            val response = withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val body = client.newCall(request).execute().body()
                println(body.contentType())
                body.bytes()
            }
            file.writeBytes(response)

            println(file.canRead())

            display = true
        } catch (e: Exception) {
            println(e)
            error = true
            return@LaunchedEffect
        }
    }

    if (display) {
        println(file.exists())

        val context = LocalContext.current

        println(file.absolutePath)

        val pdfUri = FileProvider.getUriForFile(context, "com.yousufjamil.accorm.provider", file)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context.startActivity(intent)

        val navigator = LocalNavigator.currentOrThrow
        navigator.pop()
    } else {
        CircularProgressIndicator(color = Color.White)
    }

    if (error) {
        return false
    }

    return true
}

actual suspend fun desktopLoad(url: String): List<BitmapPainter> {
    return listOf()
}