package screens.resources

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.FileProvider
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Download
//import com.squareup.okhttp.OkHttpClient
//import com.squareup.okhttp.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import screens.resources.FileManager.context
import viewmodels.CurrentSubject
import java.io.File
import java.io.FileOutputStream

actual suspend fun downloadFile(title: String, url: String): Boolean {
//    return try {
//        println("Downloading file")
//        val response =
//            OkHttpClient().newCall(Request.Builder().url(url).build()).execute().body()?.bytes()
//        println("Downloading file")
//        val downloadsDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        println("Downloading file")
//        val file = File(downloadsDir, "${title}_${System.currentTimeMillis()}.pdf")
//        println("Downloading file")
//        file.parentFile?.mkdirs()
//        withContext(Dispatchers.IO) {
//            FileOutputStream(file).use { output ->
//                output.write(response)
//            }
//        }
//        println("File downloaded")
//        true
//    } catch (e: Exception) {
//        println("Error downloading file")
//        println(e)
//        false
//    }
    return try {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(title)
            .setDescription("Downloading file...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        true
    } catch (e: Exception) {
        false
    }
}

@Composable
actual fun openFile(title: String, url: String): Boolean {
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

    LaunchedEffect(true) {
        try {
            val contentLength: Long?
            val body: ResponseBody?

            response = withContext(Dispatchers.IO) {
                mutex.withLock {
                    val client = OkHttpClient()
                    val request = Request.Builder().url(url).build()
                    body = client.newCall(request).execute().body()
                    contentLength = body?.contentLength()
                    println(contentLength)
                    println(body?.contentType())
                    body?.bytes()
                }
            }
            display = true

            if (body?.contentType().toString() != "application/pdf") {
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
        val file = File.createTempFile("Accorm"+title.filter { it.isLetterOrDigit() }, ".pdf")
        file.writeBytes(response!!)

        val navigator = LocalNavigator.currentOrThrow

        println(file.exists())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            var icon by remember {
                mutableStateOf(FontAwesomeIcons.Solid.Download)
            }
            val coroutine = rememberCoroutineScope()
            if (CurrentSubject.getUrlFileName().contains("Paper")) {
                Image(
                    imageVector = icon,
                    contentDescription = "Download",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .zIndex(2f)
                        .clickable {
                            coroutine.launch {
                                downloadFile(
                                    title = "CIE Past Paper - ${CurrentSubject.getUrlFileName()}",
                                    url = CurrentSubject.getUrl()
                                )
                            }
                            icon = FontAwesomeIcons.Solid.CheckCircle
                        }
                        .padding(15.dp),
                    colorFilter = ColorFilter.tint(
                        Color(0xFFacacf9)
                    )
                )
            }
            PdfRendererViewCompose(
                file = file,
                modifier = Modifier.fillMaxSize()
            )
        }

        BackHandler {
            file.deleteOnExit()
            navigator.pop()
        }
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