package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.Home
import screens.resources.Resources

// Access fonts
expect val poppins: FontFamily
expect val lexend: FontFamily

// Know device
expect val device: String

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
//    Main Outer Container
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
                        SpanStyle(color = Color.White, fontFamily = poppins, fontSize = 60.sp)
                    ) {
                        append("Educate")
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
                fontWeight = FontWeight.SemiBold,
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
        }
    }
}
