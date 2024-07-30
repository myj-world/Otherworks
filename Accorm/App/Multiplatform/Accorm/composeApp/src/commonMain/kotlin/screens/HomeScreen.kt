package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.ShieldAlt
import compose.icons.fontawesomeicons.solid.Table
import screens.assets.HomeIcon

// Access fonts
expect val poppins: FontFamily
expect val lexend: FontFamily

// Know device
expect val device: String

// Main Screen
@Composable
fun HomeScreen() {
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
        Row {
            HomeIcon(
                onClick = {},
                imageVector = FontAwesomeIcons.Solid.Info,
                contentDescription = "Info"
            )
            HomeIcon(
                onClick = {},
                imageVector = FontAwesomeIcons.Solid.BookOpen,
                contentDescription = "Subjects & Content"
            )
            HomeIcon(
                onClick = {},
                imageVector = FontAwesomeIcons.Solid.Table,
                contentDescription = "Services"
            )
            HomeIcon(
                onClick = {},
                imageVector = FontAwesomeIcons.Solid.ShieldAlt,
                contentDescription = "Privacy & Terms"
            )
        }
    }
}
