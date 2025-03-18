package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Search

class PPQSearcher : Tab {
    private fun readResolve(): Any = PPQSearcher()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Search)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "PPQ Searcher",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        if (LoginStatus.getLoginStatus()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}