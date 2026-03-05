package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.EllipsisH
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.ShieldAlt
import compose.icons.fontawesomeicons.solid.Table
import compose.icons.fontawesomeicons.solid.Trophy
import screens.assets.CopyrightMessage
import screens.legal.PPTC

object MoreItems : Tab {
    private fun readResolve(): Any = AboutUs
    override val options: TabOptions
        @Composable
        get() {
            val title = "AboutUs"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.EllipsisH)
            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val extraItems = listOf("Services", "About", "Spotlight", "Privacy & Terms")
        val extraIcons = listOf(
            FontAwesomeIcons.Solid.Table,
            FontAwesomeIcons.Solid.Info,
            FontAwesomeIcons.Solid.Trophy,
            FontAwesomeIcons.Solid.ShieldAlt
        )
        val extraScreens = listOf(Services, AboutUs, Spotlight, PPTC)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            @Composable
            fun Separator() {
                Box(
                    modifier = Modifier
                        .background(Color(53, 53, 93))
                        .fillMaxWidth()
                        .height(1.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
            }

            @Composable
            fun MoreItem(
                onClick: () -> Unit,
                label: String,
                icon: ImageVector
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable {
                            onClick()
                        }
                ) {
                    Image(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Text(
                        text = label,
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 15.sp
                    )
                }
            }

            val navigator = LocalNavigator.currentOrThrow

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "More",
                fontSize = 48.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            extraItems.forEachIndexed { index, item ->
                MoreItem(
                    onClick = { navigator.push(extraScreens[index]) },
                    label = item,
                    icon = extraIcons[index]
                )
                if (index != extraItems.lastIndex) Separator()
            }
            Spacer(modifier = Modifier.height(20.dp))
            CopyrightMessage()
        }
    }
}