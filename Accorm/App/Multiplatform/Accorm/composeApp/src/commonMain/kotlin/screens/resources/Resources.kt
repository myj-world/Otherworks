package screens.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen

object Resources : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Subjects"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.BookOpen)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White, fontSize = 24.sp)) {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("Accorm ")
                        }
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Resources")
                        }
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            var grade = ""
            when (grade) {
                "" -> {
                    @Composable
                    fun Option(text: String) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .padding(5.dp)
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
                                .clickable {
                                    grade = text
                                },
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = text,
                                fontSize = 28.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Option(text = "IGCSE")
                    Option(text = "AS")
                    Option(text = "A2")
                }

                "IGCSE" -> {}
                "AS" -> {}
                else -> {}
            }

        }
    }
}