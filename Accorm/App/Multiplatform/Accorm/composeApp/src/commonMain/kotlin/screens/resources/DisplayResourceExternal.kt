package screens.resources

import analytics.LogEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import screens.HomeScreen
import screens.poppins
import viewmodels.CurrentSubject

class DisplayResourceExternal : Tab {
    private fun readResolve(): Any = DisplayResourceExternal()
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
            if (!launchURL(CurrentSubject.getUrl())) {
                Text(
                    text = "Error launching URL",
                    color = Color.Red,
                    fontSize = 28.sp,
                    fontFamily = poppins
                )
                Text(
                    text = "Please manually search ${CurrentSubject.getUrl()}",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            } else {
                val navigator = LocalNavigator.currentOrThrow
                navigator.pop()
                if (CurrentSubject.getUrl().contains("youtu.be")||CurrentSubject.getUrl().contains("youtube.com")) navigator.push(Videos) else if (CurrentSubject.getUrl().contains("discord") || CurrentSubject.getUrl().contains("instagram")) navigator.push(HomeScreen) else if (CurrentSubject.getUrl().contains("docs.google.com")) navigator.push(Syllabus) else navigator.push(Notes)
            }
        }
    }
}

@Composable
expect fun launchURL (url: String): Boolean