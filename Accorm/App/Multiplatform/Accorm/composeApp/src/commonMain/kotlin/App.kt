import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import screens.legal.PPTC
import screens.resources.Resources

@Composable
@Preview
fun App() {
    MaterialTheme {
//        HomeScreen()
        TabNavigator(HomeScreen) { tabNavigator ->
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation {
                        BottomNavigationItem(
                            selected = tabNavigator.current == HomeScreen,
                            onClick = { tabNavigator.current = HomeScreen },
                            label = { Text("Home") },
                            icon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Home,
                                    contentDescription = "Home",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            modifier = Modifier.background(
                                Color(53, 53, 93)
                            ),
                            selectedContentColor = Color(171, 171, 248)
                        )
                        BottomNavigationItem(
                            selected = tabNavigator.current == Resources,
                            onClick = { tabNavigator.current = Resources },
                            label = { Text("Subjects") },
                            icon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.BookOpen,
                                    contentDescription = "Subjects",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            modifier = Modifier.background(
                                Color(53, 53, 93)
                            ),
                            selectedContentColor = Color(171, 171, 248)
                        )
                        BottomNavigationItem(
                            selected = tabNavigator.current == Services,
                            onClick = { tabNavigator.current = Services },
                            label = { Text("Services") },
                            icon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Table,
                                    contentDescription = "Services",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            modifier = Modifier.background(
                                Color(53, 53, 93)
                            ),
                            selectedContentColor = Color(171, 171, 248)
                        )
                        BottomNavigationItem(
                            selected = tabNavigator.current == AboutUs,
                            onClick = { tabNavigator.current = AboutUs },
                            label = { Text("About") },
                            icon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Info,
                                    contentDescription = "About",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            modifier = Modifier.background(
                                Color(53, 53, 93)
                            ),
                            selectedContentColor = Color(171, 171, 248)
                        )
                        BottomNavigationItem(
                            selected = tabNavigator.current == PPTC,
                            onClick = { tabNavigator.current = PPTC },
                            label = { Text("PPTC") },
                            icon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.ShieldAlt,
                                    contentDescription = "Privacy & Terms",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            },
                            modifier = Modifier.background(
                                Color(53, 53, 93)
                            ),
                            selectedContentColor = Color(171, 171, 248)
                        )
                    }
                }
            )
        }
    }
}