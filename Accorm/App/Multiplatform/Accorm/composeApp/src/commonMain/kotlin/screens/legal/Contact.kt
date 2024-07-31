package screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ShieldAlt
import screens.device
import screens.poppins

class Contact: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Contact"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.ShieldAlt)
            return remember {
                TabOptions(
                    index = 5u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        if (device == "Android") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54))
            ) {
                Text(
                    text = "Contact Us",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp
                )
                Text(
                    text = "Email us on: contact@ginastic.co",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54))
            ) {
                Text(
                    text = "Contact Us",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp
                )
                Text(
                    text = "Email us on: contact@ginastic.co",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 28.sp
                )
            }
        }
    }
}