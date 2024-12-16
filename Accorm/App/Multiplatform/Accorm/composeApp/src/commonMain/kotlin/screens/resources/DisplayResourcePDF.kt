package screens.resources

import analytics.LogEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import kotlinx.coroutines.launch
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
        LogEvent("Load Resource ${CurrentSubject.getUrl()}")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                coroutine.launch {
                    data = desktopLoad(url = CurrentSubject.getUrl())
                    isComplete = true
                }

                if (isComplete) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
//                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        data.forEach {
                            item {
                                Image(
                                    painter = it,
                                    contentDescription = "PDF page",
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f),
                                    contentScale = ContentScale.FillWidth
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
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
        super.onDispose(screen)
    }
}
expect suspend fun downloadFile(title: String, url: String): Boolean

@Composable
expect fun openFile(title: String, url: String): Boolean

expect suspend fun desktopLoad(url: String): List<BitmapPainter>