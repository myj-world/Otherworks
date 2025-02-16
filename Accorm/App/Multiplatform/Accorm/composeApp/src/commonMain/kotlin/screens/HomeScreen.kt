package screens

import accorm.composeapp.generated.resources.Res
import accorm.composeapp.generated.resources.qa_app
import analytics.LogEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Discord
import compose.icons.fontawesomeicons.brands.Instagram
import compose.icons.fontawesomeicons.solid.ArrowRight
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.Cross
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.SkullCrossbones
import compose.icons.fontawesomeicons.solid.Times
import compose.icons.fontawesomeicons.solid.Trophy
import compose.icons.fontawesomeicons.solid.User
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import screens.resources.DisplayResourceExternal
import screens.resources.Resources
import viewmodels.CurrentSubject

// Access fonts
expect val poppins: FontFamily
expect val lexend: FontFamily

// Know device
expect val device: String
expect val deviceCompatibleWithBlur: Boolean

// Tablet in Landscape
@get:Composable
expect val landscapeTablet: Boolean

// Main Screen
object HomeScreen : Tab {
    private fun readResolve(): Any = HomeScreen
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("Home Screen", null, null)

        var displayQuestionAndAnswerMsg by remember {
            mutableStateOf(true)
        }
        val navigator = LocalNavigator.current

        if (displayQuestionAndAnswerMsg) {
            Dialog(
                onDismissRequest = {
                    displayQuestionAndAnswerMsg = false
                },
                content = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box (
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Times,
                                contentDescription = "Close",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(60.dp)
                                    .clickable {
                                        displayQuestionAndAnswerMsg = false
                                    }
                                    .padding(20.dp)
                                    .zIndex(2f)
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .zIndex(1f)
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.qa_app),
                                    contentDescription = "Question and Answer App Illustration",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                Text(
                                    text = "Ask Real Toppers, Not Just Experts!",
                                    fontSize = 25.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF181829),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        append("Our ")
                                        withStyle(SpanStyle(color = Color(0xff754bff))) {
                                            append("Distinction Holders")
                                        }
                                        append(" are here to guide you: clear your doubts, ask strategies, learn from their experiences before it's too late!")
                                    },
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF1f1f36),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            navigator?.push(Spotlight)
                                        }
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Button(
                                    onClick = {
                                        CurrentSubject.setUrl("https://ginastic.co/home/login/")
                                        navigator?.push(DisplayResourceExternal())
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF1f1f36)
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = 3.dp)
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 16.dp,
                                                bottomEnd = 16.dp
                                            )
                                        )
                                ) {
                                    Text(
                                        text = "Login to ask",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFFffffff)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.ArrowRight,
                                        contentDescription = "Open",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }

        //    Main Outer Container

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            listOf(
                                Color(153, 109, 194),
                                Color(106, 106, 193)
                            ),
                            radius = 1500f,
                            center = Offset(-0.5f, -0.5f)
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//        Central Home Screen Text
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 60.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("Educate")
                            withStyle(
                                SpanStyle(
                                    color = Color(144, 144, 214, 255)
                                )
                            ) {
                                if (device == "Android" && !landscapeTablet) {
                                    append(".\n\n\n")
                                } else {
                                    append(". ")
                                }
                            }
                            append("Empower")
                            withStyle(
                                SpanStyle(
                                    color = Color(144, 144, 214, 255)
                                )
                            ) {
                                if (device == "Android") {
                                    append(".\n\n\n")
                                } else {
                                    append(". ")
                                }
                            }
                            append("Excel")
                            withStyle(
                                SpanStyle(
                                    color = Color(144, 144, 214, 255)
                                )
                            ) {
                                if (device == "Android") {
                                    append(".\n\n")
                                } else {
                                    append(". ")
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Where students and educational \ncontent blend",
                    color = Color(198, 197, 250),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                val navigator = LocalTabNavigator.current
                Button(
                    onClick = {
                        navigator.current = Resources
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(100)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(144, 144, 214)
                    )
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.BookOpen,
                        contentDescription = "Subjects",
                        tint = Color.White,
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Get Started",
                        color = Color.White,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(color = Color(88, 101, 242))
                            .padding(10.dp)
                            .clickable {
                                CurrentSubject.setUrl("https://discord.gg/nbmc3TnrMS")
                                navigator.current = DisplayResourceExternal()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Brands.Discord,
                            contentDescription = "Discord logo",
                            modifier = Modifier
                                .size(20.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(color = Color.White)
                            .padding(10.dp)
                            .clickable {
                                CurrentSubject.setUrl("https://www.instagram.com/accorm_official/")
                                navigator.current = DisplayResourceExternal()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Brands.Instagram,
                            contentDescription = "Instagram logo",
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                                .drawWithContent {
                                    val colors = listOf(
                                        Color(129, 52, 175),
                                        Color(221, 42, 123)
                                    )
                                    drawContent()
                                    drawRect(
                                        brush = Brush.linearGradient(
                                            colors = colors,
                                            start = Offset(0f, size.height),
                                            end = Offset(size.width, 0f)
                                        ),
                                        blendMode = BlendMode.DstIn
                                    )
                                }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .width(350.dp)
            ) {
                val pages = listOf(
                    Page(
                        title = "Meet the team",
                        icon = FontAwesomeIcons.Solid.User,
                        clickDestination = AboutUs
                    ),
                    Page(
                        title = "Meet the distinction holders",
                        icon = FontAwesomeIcons.Solid.Trophy,
                        clickDestination = Spotlight
                    )
                )

                val pagerState = rememberPagerState { pages.size }
                val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

                val pageInteractionSource = remember { MutableInteractionSource() }
                val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

                val autoAdvance = !pagerIsDragged && !pageIsPressed

                if (autoAdvance) {
                    LaunchedEffect(pagerState, pageInteractionSource) {
                        while (true) {
                            delay(2000)
                            val nextPage = (pagerState.currentPage + 1) % pages.size
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                }

                val navigator = LocalNavigator.current

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) { page ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(100))
                            .background(
                                Color(0xFF25254b)
                            )
                            .clickable(
                                interactionSource = pageInteractionSource,
                                indication = LocalIndication.current
                            ) {
                                navigator?.push(pages[page].clickDestination)
                            }
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(100)
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = pages[page].icon,
                            contentDescription = pages[page].title,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = pages[page].title,
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(if (device != "Android") 20.dp else 100.dp))
            }
        }
    }
}

data class Page(
    val title: String,
    val icon: ImageVector,
    val clickDestination: Screen
)

expect suspend fun Connected(): Boolean
