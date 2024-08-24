package screens.resources

import android.content.Intent
import android.os.Environment
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

actual suspend fun downloadFile(url: String): Boolean {
    return try {
        val response =
            OkHttpClient().newCall(Request.Builder().url(url).build()).execute().body().bytes()
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
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
    var display by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf(false)
    }
    var response by remember {
        mutableStateOf<ByteArray?>(null)
    }
    val mutex = Mutex()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val file = File.createTempFile("file", "pdf")
    file.deleteOnExit()
    LaunchedEffect(true) {
        try {
            val contentLength: Long
            val body: com.squareup.okhttp.ResponseBody

            response = withContext(Dispatchers.IO) {
                mutex.withLock {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    body = client.newCall(request).execute().body()
                    contentLength = body.contentLength()
                    println(contentLength)
                    println(body.contentType())
                    body.bytes()
                }
            }
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    mutex.withLock {
                        FileOutputStream(file).use { output ->
                            output.write(response)
                        }
                    }
                    println("File downloaded")
                    println(file.length())
                    val pdfUri = FileProvider.getUriForFile(context, "com.yousufjamil.accorm.provider", file)

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(pdfUri, "application/pdf")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    context.startActivity(intent)

                    display = true
                }
            }

//            file.writeBytes(response)

            if (body.contentType().toString() != "application/pdf") {
                println("Not a pdf")
                error = true
                return@LaunchedEffect
            } else if (response == null) {
                println("Response is null")
                error = true
                return@LaunchedEffect
            } else {
//                display = true
            }
        } catch (e: Exception) {
            println(e)
            error = true
            return@LaunchedEffect
        }
    }

    if (display && !error) {
        val navigator = LocalNavigator.currentOrThrow
        navigator.pop()
    } else if (!error) {
        CircularProgressIndicator(color = Color.White)
    } else {
        return false
    }

    return true
}

actual suspend fun desktopLoad(url: String): List<BitmapPainter> {
    return listOf()
}