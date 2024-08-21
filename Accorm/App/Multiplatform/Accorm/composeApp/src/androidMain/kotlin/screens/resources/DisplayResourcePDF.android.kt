package screens.resources

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pratikk.jetpdfvue.HorizontalVueReader
import com.pratikk.jetpdfvue.VueHorizontalSlider
import com.pratikk.jetpdfvue.state.VueFileType
import com.pratikk.jetpdfvue.state.VueLoadState
import com.pratikk.jetpdfvue.state.VueResourceType
import com.pratikk.jetpdfvue.state.rememberHorizontalVueReaderState
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.RedoAlt
import compose.icons.fontawesomeicons.solid.Undo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import screens.poppins
import viewmodels.CurrentSubject
import java.io.File

actual suspend fun downloadFile(url: String): Boolean {
    return true
}

@Composable
actual fun openFile(url: String): Boolean {
    var display by remember {
        mutableStateOf(false)
    }

    val file = File.createTempFile("file", ".pdf")
    file.deleteOnExit()
    LaunchedEffect(true) {
        val response = withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val body = client.newCall(request).execute().body()
            println(body.contentType())
            body.bytes()
        }
        file.writeBytes(response)

        display = true
    }

    if (display) {
        val parcelFileDescriptor =
            ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer: PdfRenderer
        try {
            pdfRenderer = PdfRenderer(parcelFileDescriptor)
        } catch (e: Exception) {
            println(e)
            return false
        }
        val pages = mutableListOf<ImageBitmap>()
        for (i in 0 until pdfRenderer.pageCount) {
            val page = pdfRenderer.openPage(i)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            pages.add(bitmap.asImageBitmap())
            page.close()
        }
        pdfRenderer.close()
        parcelFileDescriptor.close()
        file.delete()

        LazyColumn {
            pages.forEach { image ->
                item {
                    Image(
                        bitmap = image,
                        contentDescription = "PDF page"
                    )
                }
            }
        }
    } else {
        CircularProgressIndicator(color = Color.White)
    }

//    val urlLoad = url
//    val pdfState = rememberVerticalPdfReaderState(
//        resource = ResourceType.Remote(urlLoad),
//        isZoomEnable = true
//    )

//    var display by remember {
//        mutableStateOf(false)
//    }
//
//    val horizontalVueReaderState = rememberHorizontalVueReaderState(
//        resource = VueResourceType.Remote(
//            url = url,
//            fileType = VueFileType.PDF
//        )
//    )
//
//    var check by remember {
//        mutableStateOf(true)
//    }
//
//    @Composable
//    fun Check() {
//        check = false
//        println("Check rerun successfully")
//        when (horizontalVueReaderState.vueLoadState) {
//            VueLoadState.DocumentLoaded -> {
//                display = true
//            }
//
//            is VueLoadState.DocumentError -> {
//                println(horizontalVueReaderState.vueLoadState.getErrorMessage)
//            }
//
//            VueLoadState.DocumentImporting -> {
//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        check = true
//                        println("Importing Check rerun posted")
//                    },
//                    1000
//                )
//            }
//            VueLoadState.DocumentLoading -> {
//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        check = true
//                        println("Loading ${horizontalVueReaderState.loadPercent} Check rerun posted")
//                    },
//                    1000
//                )
//            }
//            VueLoadState.NoDocument -> {
//                println("No Document ${horizontalVueReaderState.vueLoadState.getErrorMessage}")
//            }
//            else -> {
//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        check = true
//                        println("None Check rerun posted")
//                    },
//                    1000
//                )
//            }
//        }
//    }
//
//    if (check) Check()

//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        if (display) {
//            val background = Modifier
//                .background(
//                    Color(172, 172, 249)
//                )
//                .border(
//                    width = 1.dp,
//                    color = Color(172, 172, 249),
//                    shape = MaterialTheme.shapes.small
//                )
//                .clip(MaterialTheme.shapes.small)
//            val iconTint = Color(31, 31, 54)
//
//            HorizontalVueReader(
//                modifier = Modifier.fillMaxSize(),
//                contentModifier = Modifier.fillMaxSize(),
//                horizontalVueReaderState = horizontalVueReaderState
//            )
//            Row(
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(horizontal = 8.dp, vertical = 12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "${horizontalVueReaderState.currentPage} of ${horizontalVueReaderState.pdfPageCount}",
//                    modifier = Modifier
//                        .then(background)
//                        .padding(10.dp)
//                )
//                Spacer(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                )
//                Row {
//                    val context = LocalContext.current
//                    IconButton(
//                        modifier = background,
//                        onClick = { //Share
//                            horizontalVueReaderState.sharePDF(context)
//                        }) {
//                        Icon(
//                            imageVector = Icons.Filled.Share,
//                            contentDescription = "Share",
//                            tint = iconTint
//                        )
//                    }
//                }
//            }
//            Column(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(horizontal = 8.dp, vertical = 12.dp)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Spacer(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxWidth()
//                    )
//                    IconButton(
//                        modifier = background,
//                        onClick = {
//                            //Rotate
//                            horizontalVueReaderState.rotate(-90f)
//                        }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.Undo,
//                            contentDescription = "Rotate Left",
//                            tint = iconTint
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(5.dp))
//                    IconButton(
//                        modifier = background,
//                        onClick = {
//                            //Rotate
//                            horizontalVueReaderState.rotate(90f)
//                        }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.RedoAlt,
//                            contentDescription = "Rotate Right",
//                            tint = iconTint
//                        )
//                    }
//                    Spacer(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxWidth()
//                    )
//                }
//                VueHorizontalSlider(
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp, vertical = 10.dp)
//                        .fillMaxWidth()
//                        .height(40.dp),
//                    horizontalVueReaderState = horizontalVueReaderState
//                )
//            }
//        } else {
//            CircularProgressIndicator(color = Color.White)
//        }

//        VerticalPDFReader(
//            state = pdfState,
//            modifier = Modifier
//                .fillMaxSize()
//        )
//        if (!pdfState.isLoaded) {
//            CircularProgressIndicator(
//                color = Color.White,
//            )
//        }
//
//        val horizontalVueReaderState = rememberHorizontalVueReaderState(
//            resource = VueResourceType.Remote(
//                url = url,
//                fileType = VueFileType.PDF
//            )
//        )
//
//        var displayError by remember {
//            mutableStateOf(false)
//        }
//
//        var loading by remember {
//            mutableStateOf(false)
//        }
//
//        when (horizontalVueReaderState.vueLoadState) {
//            is VueLoadState.DocumentError -> {
//                loading = false
//                displayError = true
//            }
//            VueLoadState.DocumentImporting -> {
//                loading = true
//            }
//            VueLoadState.DocumentLoaded -> {
//                loading = false
//            }
//            VueLoadState.DocumentLoading -> {
//                loading = true
//            }
//            VueLoadState.NoDocument -> {
//                loading = false
//                displayError = true
//            }
//        }
//
//        if (displayError) {
//            Text(
//                text = "Error loading File",
//                color = Color.Red,
//                fontSize = 28.sp,
//                fontFamily = poppins
//            )
//        }
//
//        if (loading) {
//            CircularProgressIndicator(color = Color.White)
//        } else {
//            if (!displayError) {
//                HorizontalVueReader(
//                    horizontalVueReaderState = horizontalVueReaderState
//                )
//            }
//        }

//        val coroutine = rememberCoroutineScope()
//        val navigator = LocalNavigator.currentOrThrow
//
//        fun Check() {
//            if (urlLoad != CurrentSubject.getUrl()) {
//                pdfState.close()
//                navigator.pop()
//                navigator.push(DisplayResourcePDF())
//            } else {
//                coroutine.launch {
//                    delay(5000)
//                    Check()
//                }
//            }
//        }
//        Check()
//    }
    return true
}

actual suspend fun desktopLoad(url: String): List<BitmapPainter> {
    return listOf()
}