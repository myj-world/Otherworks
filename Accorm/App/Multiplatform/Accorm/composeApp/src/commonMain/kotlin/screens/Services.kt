package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import compose.icons.fontawesomeicons.solid.Bars
import compose.icons.fontawesomeicons.solid.BezierCurve
import compose.icons.fontawesomeicons.solid.File
import compose.icons.fontawesomeicons.solid.Table
import compose.icons.fontawesomeicons.solid.Video
import screens.assets.CopyrightMessage
import screens.assets.Service

object Services : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Services"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Table)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column (
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
            Service(
                title = "Revision Notes",
                description = "Important for reviewing main syllabus concepts.",
                icon = FontAwesomeIcons.Solid.File
            )
            Service(
                title = "Educational Videos",
                description = "Great tool for concept-building.",
                icon = FontAwesomeIcons.Solid.Video
            )
            Service(
                title = "Past Paper Questions",
                description = "Essential for practicing and understanding exam patterns.",
                icon = FontAwesomeIcons.Solid.BezierCurve
            )
            Service(
                title = "Syllabus Chapters & Outlines",
                description = "Syllabus documents & chapter-wise resources listing.",
                icon = FontAwesomeIcons.Solid.Bars
            )
            Spacer(modifier = Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}