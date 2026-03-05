package screens.assets

import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screens.resources.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.poppins

@Composable
fun Service(
    title: String,
    description: String,
    icon: ImageVector
) {
    val navigator = LocalNavigator.currentOrThrow
    fun itemClick() {
        navigator.push(Resources)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(10.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(153, 109, 194),
                        Color(106, 106, 193)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable{ itemClick() }
            .padding(15.dp)
    ) {
        Box (
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(100))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(118, 78, 255),
                modifier = Modifier
                    .size(20.dp)
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontFamily = poppins,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Text(
            text = description,
            color = Color.White,
            fontFamily = poppins,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}