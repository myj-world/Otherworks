package screens.resources

import accorm.composeapp.generated.resources.Res
import accorm.composeapp.generated.resources.zoomin
import accorm.composeapp.generated.resources.zoomout
import analytics.LogEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Download
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import screens.device
import screens.poppins
import viewmodels.CurrentSubject

var data = listOf<BitmapPainter>()
class DisplayResourcePDF : Tab, ScreenLifecycleOwner {
    private fun readResolve(): Any = DisplayResourcePDF()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Resource",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("Load Resource ${CurrentSubject.getUrl()}", null, null)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54)),
            contentAlignment = Alignment.Center
        ) {
            val coroutine = rememberCoroutineScope()
            val navigator = LocalNavigator.currentOrThrow
            var show1 by remember { mutableStateOf(false) }
            var show2 by remember { mutableStateOf(false) }

            if (show1) {
                AlertDialog(
                    onDismissRequest = { navigator.pop() },
                    confirmButton = {
                        Button(onClick = { navigator.pop() }) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(
                            text = "An error occurred."
                        )
                    },
                    text = {
                        Text(
                            text = "Please try again later."
                        )
                    }
                )
            }
            if (show2) {
                AlertDialog(
                    onDismissRequest = {
                        navigator.pop()
                    },
                    title = {
                        Text(
                            text = "File saved to downloads",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            color = Color(0xFF181829)
                        )
                    },
                    text = {
                        Text(
                            text = "Please check the downloads folder for file.pdf",
                            fontSize = 18.sp,
                            fontFamily = poppins,
                            color = Color(0xFF1f1f36)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = { navigator.pop() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF1f1f36)
                            )
                        ) {
                            Text(
                                text = "Got it.",
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFFffffff)
                            )
                        }
                    },
                    backgroundColor = Color.White
                )
            }

//            val complete = if (device != "Android") coroutine.launch {
//                val status =
//                    downloadFile(url = CurrentSubject.getUrl())
//                if (!status) show1 = true else show2 = true
//            }.isCompleted else openFile(url = CurrentSubject.getUrl())
//            if (!complete && device != "Android") {
//                Text(
//                    text = "Downloading File...",
//                    color = Color.White,
//                    fontFamily = poppins,
//                    fontSize = 30.sp,
//                    modifier = Modifier
//                        .padding(20.dp)
//                )
//            }
            val isDownload = CurrentSubject.checkIsDownload()

            if (device == "Android") {
                val url = CurrentSubject.getUrl()
                val urlFileName = CurrentSubject.getUrlFileName()
                val opened = openFile(title = urlFileName, url = url)
                if (!opened) {
                    Text(
                        text = "An error occurred.",
                        color = Color.Red,
                        fontFamily = poppins,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                }
            } else {
                var isComplete by remember { mutableStateOf(false) }
                if (!isDownload) {
                    coroutine.launch {
                        data = desktopLoad(url = CurrentSubject.getUrl())
                        isComplete = true
                    }
                } else {
                    data = CurrentSubject.getImages()
                    isComplete = true
                }

                if (isComplete && data.isNotEmpty()) {
                    var pdfZoom by remember { mutableStateOf(0.8f) }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(50.dp)
                            .zIndex(2f),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Row {
                            Image(
                                painter = painterResource(Res.drawable.zoomin),
                                contentDescription = "Zoom in",
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(25.dp))
                                    .clickable (enabled = pdfZoom < 0.9f) { if (pdfZoom < 0.9f) pdfZoom += 0.1f }
                                    .padding(15.dp),
                                colorFilter = if (pdfZoom >= 0.9f) ColorFilter.tint(Color.Gray) else ColorFilter.tint(Color(0xFFacacf9))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(
                                painter = painterResource(Res.drawable.zoomout),
                                contentDescription = "Zoom out",
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(25.dp))
                                    .clickable (enabled = pdfZoom > 0.3f) { if (pdfZoom > 0.3f) pdfZoom -= 0.1f }
                                    .padding(15.dp),
                                colorFilter = if (pdfZoom <= 0.3f) ColorFilter.tint(Color.Gray) else ColorFilter.tint(Color(0xFFacacf9))
                            )
                            if (CurrentSubject.getUrlFileName().contains("Paper")) {
                                var icon by remember {
                                    mutableStateOf(FontAwesomeIcons.Solid.Download)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Image(
                                    imageVector = icon,
                                    contentDescription = "Download",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(25.dp))
                                        .clickable {
                                            coroutine.launch {
                                                val fileName = "CIE Past Paper - ${CurrentSubject.getUrlFileName()}.pdf".replace(oldChar = '/', newChar = '-')
                                                downloadFile(
                                                    title = fileName,
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
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        data.forEach {
                            item {
                                Image(
                                    painter = it,
                                    contentDescription = "PDF page",
                                    modifier = Modifier
                                        .fillMaxWidth(pdfZoom),
                                    contentScale = ContentScale.FillWidth
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                } else if (isComplete) {
                    Text(
                        text = "An error occurred.",
                        color = Color.Red,
                        fontFamily = poppins,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                } else {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
        }
    }

    override fun onDispose(screen: Screen) {
        CurrentSubject.setUrl("")
        CurrentSubject.setImages(emptyList())
        CurrentSubject.updateIsDownload(false)
        super.onDispose(screen)
    }
}
expect suspend fun downloadFile(title: String, url: String): Boolean

@Composable
expect fun openFile(title: String, url: String): Boolean

expect suspend fun desktopLoad(url: String): List<BitmapPainter>