package screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowAltCircleRight
import compose.icons.fontawesomeicons.solid.Blog
import compose.icons.fontawesomeicons.solid.File
import compose.icons.fontawesomeicons.solid.Trophy
import screens.assets.CopyrightMessage
import screens.resources.DisplayResourceExternal
import viewmodels.CurrentSubject
import kotlin.random.Random

object Spotlight : Tab {
    private fun readResolve(): Any = Spotlight
    override val options: TabOptions
        @Composable
        get() {
            val title = "Spotlight"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Trophy)
            return remember {
                TabOptions(
                    index = 99u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val infiniteTransition = rememberInfiniteTransition()
        val height by infiniteTransition.animateFloat(
            initialValue = 100f,
            targetValue = 250f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val width by infiniteTransition.animateFloat(
            initialValue = 400f,
            targetValue = 550f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val rotation by infiniteTransition.animateFloat(
            initialValue = -20f,
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 10000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        var padding by remember {
            mutableStateOf(Random.nextInt(100))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (deviceCompatibleWithBlur) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF181f40))
                        .blur(100.dp)
                        .zIndex(0f),
                    contentAlignment = Alignment.Center
                ) {

                    padding = Random.nextInt(100)

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(start = 50.dp)
                            .background(Color.Transparent),
                        onDraw = {
                            rotate(rotation) {
                                drawRoundRect(
                                    brush = Brush
                                        .linearGradient(
                                            colors = listOf(
                                                Color(0xFF9452fd),
                                                Color(0xFF345dff)
                                            ),
                                            start = Offset(0f, size.height / 2),
                                            end = Offset(size.width, size.height / 2)
                                        ),
                                    size = Size(width, height)
                                )
                            }
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .zIndex(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val distinctionHolders = listOf(
                    DistinctionHolder(
                        name = "M. Abdullah Kamil",
                        icon = "A",
                        achievement = "Top in Saudia Arabia - Cambridge IGCSE™ Accounting & Computer Science"
                    ),
                    DistinctionHolder(
                        name = "Tahreem Awan",
                        icon = "T",
                        achievement = "Top in Western Province, Saudi Arabia  - Cambridge IGCSE™ Information & Communication Technology"
                    ),
                    DistinctionHolder(
                        name = "Muhammad Arslan",
                        icon = "M",
                        achievement = "Top in The World  - Cambridge IGCSE™ Mathematics"
                    ),
                    DistinctionHolder(
                        name = "M. Khalid Iqbal",
                        icon = "K",
                        achievement = "Best Across Four  - Cambridge AS Level, First Place in Saudi Arabia and Western Province"
                    ),
                    DistinctionHolder(
                        name = "M. Khalid Iqbal",
                        icon = "K",
                        achievement = "Top in Western Province, Saudi Arabia  - Cambridge AS Level Computer Science"
                    ),
                    DistinctionHolder(
                        name = "Mahnoor Faisal",
                        icon = "M",
                        achievement = "Best Across Three  - Cambridge AS Level, Third Place in Western Province"
                    ),
                    DistinctionHolder(
                        name = "Ayesha Amir",
                        icon = "A",
                        achievement = "Top in Saudi Arabia - Cambridge IGCSE Computer Science"
                    )
                )

                @Composable
                fun DisplayDistinctionHolder(
                    person: DistinctionHolder
                ) {
                    Column(
                        modifier = Modifier
                            .width(300.dp)
                            .height(400.dp)
                            .padding(20.dp)
                            .clip(RoundedCornerShape(25))
                            .background(Color(0xFF1c2651))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(100))
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(100.dp),
                                    ambientColor = Color(
                                        red = 0f,
                                        blue = 0f,
                                        green = 0f,
                                        alpha = 0.7f
                                    )
                                )
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFF9452fd),
                                            Color(0xFF345dff)
                                        ),
                                        radius = 200f,
                                        center = Offset(-10f, -10f)
                                    ),
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .border(
                                    width = 4.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF345dff),
                                            Color(0xFF9452fd),
                                            Color(0xFF345dff),
                                            Color(0xFF9452fd),
                                            Color(0xFF345dff),
                                            Color(0xFF9452fd),
                                            Color(0xFF345dff),
                                            Color(0xFF9452fd)
                                        )
                                    ),
                                    shape = RoundedCornerShape(100.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = person.icon,
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = person.name,
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = person.achievement,
                            color = Color.Gray,
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(Modifier.height(30.dp))
                            Text(
                                text = "Your limitation - it's only your imagination",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                fontFamily = poppins
                            )
                            Spacer(Modifier.height(20.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = Color.Gray)) {
                                        append("Our Team of ")
                                    }
                                    withStyle(SpanStyle(color = Color(0xff9aaaf5))) {
                                        append("Experienced")
                                    }
                                    withStyle(SpanStyle(color = Color.Gray)) {
                                        append(" and ")
                                    }
                                    withStyle(SpanStyle(color = Color(0xff9aaaf5))) {
                                        append("World-Class Achievers")
                                    }
                                    withStyle(SpanStyle(color = Color.Gray)) {
                                        append(".")
                                    }
                                },
                                textAlign = TextAlign.Center,
                                fontFamily = poppins,
                                fontSize = 18.sp
                            )
                        }
                    }

                    items(distinctionHolders.size) {
                        DisplayDistinctionHolder(person = distinctionHolders[it])
                    }

                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val navigator = LocalNavigator.currentOrThrow
                            val androidModifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(Color(0xFF1c2651), RoundedCornerShape(25))
                                .padding(20.dp)
                            val desktopModifier = Modifier
                                .width(500.dp)
                                .background(Color(0xFF1c2651), RoundedCornerShape(25))
                                .padding(20.dp)

                            Spacer(Modifier.height(100.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = if (device == "Android") androidModifier else desktopModifier
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.File,
                                        contentDescription = "Q&A App",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = "Q&A App",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = poppins
                                    )
                                }
                                Button(
                                    onClick = {
                                        CurrentSubject.setUrl("https://apps.ginastic.co/apps/q&a")
                                        navigator.push(DisplayResourceExternal())
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(25))
                                        .padding(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF345dff)
                                    )
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Open",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = poppins
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.ArrowAltCircleRight,
                                            contentDescription = "Open",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(20.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = if (device == "Android") androidModifier else desktopModifier
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.Blog,
                                        contentDescription = "Blogs",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = "Blogs",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = poppins
                                    )
                                }
                                Button(
                                    onClick = {
                                        CurrentSubject.setUrl("https://blogs.ginastic.co/")
                                        navigator.push(DisplayResourceExternal())
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(25))
                                        .padding(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF345dff)
                                    )
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Open",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = poppins
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.ArrowAltCircleRight,
                                            contentDescription = "Open",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(100.dp))
                            CopyrightMessage()
                            Spacer(Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}

data class DistinctionHolder(
    val name: String,
    val icon: String,
    val achievement: String
)