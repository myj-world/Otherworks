package screens.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ShieldAlt
import screens.device
import screens.landscapeTablet
import screens.poppins
import viewmodels.CurrentEmailName

object Contact: Tab {
    private fun readResolve(): Any = Contact
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
        if (device == "Android" && !landscapeTablet) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54))
                    .padding(20.dp)
            ) {
                Text(
                    text = "Contact ${CurrentEmailName.getName()}",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp
                )
                Text(
                    text = "Email on: ${CurrentEmailName.getEmail()}",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(100.dp))
                CopyrightMessage()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54))
                    .padding(20.dp)
            ) {
                Text(
                    text = "Contact ${CurrentEmailName.getName()}",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp
                )
                Text(
                    text = "Email on: ${CurrentEmailName.getEmail()}",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 28.sp
                )
                Spacer(Modifier.height(100.dp))
                CopyrightMessage()
            }
        }
    }
}