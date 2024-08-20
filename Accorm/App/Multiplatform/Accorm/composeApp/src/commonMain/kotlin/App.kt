import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.ShieldAlt
import compose.icons.fontawesomeicons.solid.Table
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.AboutUs
import screens.HomeScreen
import screens.Services
import screens.device
import screens.landscapeTablet
import screens.legal.PPTC
import screens.resources.Resources

@Composable
@Preview
fun App() {
    MaterialTheme {
        val items = listOf("Home", "Subjects", "Services", "About", "PPTC")
        val icons = listOf(
            FontAwesomeIcons.Solid.Home,
            FontAwesomeIcons.Solid.BookOpen,
            FontAwesomeIcons.Solid.Table,
            FontAwesomeIcons.Solid.Info,
            FontAwesomeIcons.Solid.ShieldAlt
        )
        val screens = listOf(HomeScreen, Resources, Services, AboutUs, PPTC)
        TabNavigator(
            tab = HomeScreen
        ) { tabNavigator ->
            Scaffold(
                content = {
                    Row {
                        if (device != "Android" || landscapeTablet) {
                            NavigationRail (
                                backgroundColor = Color(53, 53, 93)
                            ) {
                                items.forEachIndexed { index, item ->
                                    NavigationRailItem(
                                        icon = {
                                            Icon(
                                                icons[index],
                                                contentDescription = item,
                                                tint = if (tabNavigator.current != screens[index]) Color.White else Color(
                                                    171,
                                                    171,
                                                    248
                                                ),
                                                modifier = Modifier
                                                    .size(24.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                item,
                                                color = if (tabNavigator.current != screens[index]) Color.White else Color(
                                                    171,
                                                    171,
                                                    248
                                                )
                                            )
                                        },
                                        selected = tabNavigator.current == screens[index],
                                        onClick = { tabNavigator.current = screens[index] },
                                        modifier = Modifier.background(
                                            Color(53, 53, 93)
                                        )
                                    )
                                }
                            }
                        }
                        CurrentTab()
                    }
                },
                bottomBar = {
                    if (device == "Android" && !landscapeTablet) {
                        BottomNavigation {
                            items.forEachIndexed { index, item ->
                                BottomNavigationItem(
                                    selected = tabNavigator.current == screens[index],
                                    onClick = { tabNavigator.current = screens[index] },
                                    label = { Text(item) },
                                    icon = {
                                        Icon(
                                            imageVector = icons[index],
                                            contentDescription = item,
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                    },
                                    modifier = Modifier.background(
                                        Color(53, 53, 93)
                                    ),
                                    selectedContentColor = Color(171, 171, 248),
                                    unselectedContentColor = Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}