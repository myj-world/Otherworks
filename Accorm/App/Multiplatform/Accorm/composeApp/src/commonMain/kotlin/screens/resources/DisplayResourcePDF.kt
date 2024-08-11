package screens.resources

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import kotlinx.coroutines.launch
import screens.assets.CopyrightMessage
import screens.device
import screens.poppins
import viewmodels.CurrentSubject

var data = listOf<BitmapPainter>()
object DisplayResource : Tab {
    private fun readResolve(): Any = DisplayResource
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
                    onDismissRequest = { navigator.pop() },
                    confirmButton = {
                        Button(onClick = { navigator.pop() }) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(
                            text = "File saved to downloads"
                        )
                    },
                    text = {
                        Text(
                            text = "Please check your downloads folder. The file is named file.pdf"
                        )
                    }
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
                openFile(url = CurrentSubject.getUrl())
            } else {
                var isComplete by remember { mutableStateOf(false) }
                coroutine.launch {
                    data = desktopLoad(url = CurrentSubject.getUrl())
                    isComplete = true
                }

                if (isComplete) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        data.forEach {

                            Image(
                                painter = it,
                                contentDescription = "PDF page"
                            )
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
}

expect suspend fun downloadFile(url: String): Boolean

@Composable
expect fun openFile(url: String): Boolean

expect suspend fun desktopLoad(url: String): List<BitmapPainter>