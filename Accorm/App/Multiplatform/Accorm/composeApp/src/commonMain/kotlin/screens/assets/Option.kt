package screens.assets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.device
import screens.landscapeTablet

@Composable
fun Option(
    text: String,
    onClick: () -> Unit
) {
    val optionModifier = if (device == "Android" && !landscapeTablet) {
        Modifier
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
                onClick()
            }
    } else {
        Modifier
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
                onClick()
            }
    }

    Box(
        modifier = optionModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}