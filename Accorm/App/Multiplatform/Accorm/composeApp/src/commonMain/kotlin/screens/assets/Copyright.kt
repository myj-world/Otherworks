package screens.assets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import screens.poppins

@Composable
fun CopyrightMessage() {
    Text(
        text = "© Copyright 2023-2024, Accorm of Ginastic. App developed by MYJ World. All rights reserved.",
        color = Color(128, 128, 128),
        fontFamily = poppins,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}