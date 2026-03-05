package screens.assets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import screens.poppins
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CopyrightMessage() {
    val format = DateTimeFormatter.ofPattern("yyyy")
    Text(
        text = "© Copyright 2023-${LocalDateTime.now().format(format)}, Accorm of Ginastic. App developed by MYJ World. Content Provided by Accorm Web. All rights reserved.",
        color = Color(128, 128, 128),
        fontFamily = poppins,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}