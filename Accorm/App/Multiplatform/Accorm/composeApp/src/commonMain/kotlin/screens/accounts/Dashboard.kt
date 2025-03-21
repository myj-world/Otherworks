package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SignOutAlt
import compose.icons.fontawesomeicons.solid.User
import screens.assets.CopyrightMessage
import screens.assets.Option
import screens.device
import screens.landscapeTablet
import screens.poppins
import screens.resources.parseColor

object Dashboard : Tab {
    private fun readResolve(): Any = Dashboard
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.User)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Dashboard",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
//        var response by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!LoginStatus.getLoginStatus()) {
                LoginStatus.clearSavedLoginData()
                val navigator = LocalNavigator.currentOrThrow
                navigator.push(Login)
            } else {
                val navigator = LocalNavigator.currentOrThrow

                LazyVerticalGrid (columns = if (device == "Android" && !landscapeTablet) GridCells.Fixed(1) else GridCells.Adaptive(minSize = 300.dp)) {
                    item (
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val bg = parseColor(LoginStatus.getLogoBg())

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth(if (device == "Android" && !landscapeTablet) 0.9f else 0.5f)
                                    .border(
                                        width = 2.dp,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                Color(118, 78, 255),
                                                Color(157, 78, 255)
                                            )
                                        ),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFF35354a))
                                    .padding(20.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(96.dp)
                                        .height(96.dp)
                                        .clip(RoundedCornerShape(100.dp))
                                        .background(bg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = LoginStatus.getLogo(),
                                        color = Color.White,
                                        fontFamily = poppins,
                                        fontSize = 40.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.height(30.dp))
                                Text(
                                    text = LoginStatus.getName(),
                                    color = Color.White,
                                    fontFamily = poppins,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = LoginStatus.getEmail(),
                                    color = Color.White,
                                    fontFamily = poppins,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Image(
                                        imageVector = FontAwesomeIcons.Solid.SignOutAlt,
                                        contentDescription = "Logout",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(5.dp)
                                            .clickable {
                                                LoginStatus.clearSavedLoginData()
                                                navigator.push(Login)
                                            },
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Option(
                            text = "Downloads",
                            onClick = { navigator.push(Downloads()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item {
                        Option(
                            text = "Favourites",
                            onClick = { navigator.push(Favourites()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item {
                        Option(
                            text = "History",
                            onClick = { navigator.push(History()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item {
                        Option(
                            text = "My Todo List",
                            onClick = { navigator.push(ToDoList()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item {
                        Option(
                            text = "PPQ Searcher",
                            onClick = { navigator.push(PPQSearcher()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item {
                        Option(
                            text = "Dictionary",
                            onClick = { navigator.push(Dictionary()) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }



                Spacer(modifier = Modifier.height(50.dp))
                CopyrightMessage()
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}