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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import screens.assets.CopyrightMessage
import screens.assets.Subject
import screens.device
import screens.landscapeTablet

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
            var grade by remember { mutableStateOf("") }
            @Composable
            fun Option(text: String) {
                if (device == "Android" && !landscapeTablet) {
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
                                if (text != "Coming Soon!") {
                                    grade = text
                                }
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
                } else {
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(350.dp)
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
                                if (text != "Coming Soon!") {
                                    grade = text
                                }
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
            }
            when (grade) {
                "" -> {
                    if (device == "Android" && !landscapeTablet) {
                        Option(text = "IGCSE / O Level")
//                        Option(text = "AS")
//                        Option(text = "A2")
                    } else {
                        Row {
                            Option(text = "IGCSE / O Level")
//                            Option(text = "AS")
//                            Option(text = "A2")
                        }
                    }
                }

                "IGCSE / O Level" -> {
                    Subject(
                        title = "Accounting",
                        code = "0452/7077",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Biology",
                        code = "0610/5090",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Chemistry",
                        code = "0620/5070",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Physics",
                        code = "0625/5054",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "CS",
                        code = "0478/2210",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Maths",
                        code = "0580/4024",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "FLE",
                        code = "0500",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "ESL",
                        code = "0510",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Islamiyat",
                        code = "0493/2058",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "History",
                        code = "0448/2059",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                    Subject(
                        title = "Geography",
                        code = "0448/2059",
                        notes = true,
                        videos = true,
                        ppqs = true,
                        syllabus = true,
                        grade = "IGCSE / O Level"
                    )
                }
                "AS" -> {
                    Option(text = "Coming Soon!")
                }
                "A2" -> {
                    Option(text = "Coming Soon!")
                }
                else -> {
                    Option(text = "Error: Not Found")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}