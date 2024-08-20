package screens.resources

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import viewmodels.CurrentSubject

actual suspend fun downloadFile(url: String): Boolean {
    return true
}

@Composable
actual fun openFile(url: String): Boolean {
    val urlLoad =  url
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(urlLoad),
        isZoomEnable = true
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
        )
        if (!pdfState.isLoaded) {
            CircularProgressIndicator(
                color = Color.White,
            )
        }

        val coroutine = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        fun Check() {
            if (urlLoad != CurrentSubject.getUrl()) {
                pdfState.close()
                navigator.pop()
                navigator.push(DisplayResourcePDF())
            } else {
                coroutine.launch {
                    delay(5000)
                    Check()
                }
            }
        }
        Check()
    }
    return true
}

actual suspend fun desktopLoad(url: String) : List<BitmapPainter> {
    return listOf()
}