import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Blog
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.EllipsisH
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.User
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.HomeScreen
import screens.MoreItems
import screens.Services
import screens.device
import screens.landscapeTablet
import screens.Blogs
import screens.resources.Resources

@Composable
@Preview
fun App() {
    MaterialTheme {
        val items = listOf("Home", "Subjects", "Account", "Blogs", "More")
        val icons = listOf(
            FontAwesomeIcons.Solid.Home,
            FontAwesomeIcons.Solid.BookOpen,
            FontAwesomeIcons.Solid.User,
            FontAwesomeIcons.Solid.Blog,
            FontAwesomeIcons.Solid.EllipsisH
        )
        val screens = listOf(HomeScreen, Resources, Services, Blogs, MoreItems)
        TabNavigator(
            tab = HomeScreen
        ) { tabNavigator ->
//            val navigator = LocalNavigator.currentOrThrow
            Scaffold(
                content = {
                    Row {
                        if (device != "Android" || landscapeTablet) {
                            NavigationRail(
                                backgroundColor = Color(53, 53, 93)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    items.forEachIndexed { index, item ->
                                        if (item != "Account") {
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
                                                onClick = {
                                                    tabNavigator.current = screens[index]
                                                },
                                                modifier = Modifier.background(
                                                    Color(53, 53, 93)
                                                )
                                            )
                                        } else {
                                            Column(
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .height(92.dp)
                                                    .width(92.dp)
                                                    .padding(8.dp)
                                                    .clip(
                                                        RoundedCornerShape(100)
                                                    )
                                                    .background(
                                                        if (tabNavigator.current == screens[index]) Color(
                                                            0xFFacacf9
                                                        ) else Color(0xFF473e5f)
                                                    )
                                                    .clickable {
                                                        tabNavigator.current = screens[index]
                                                    }
                                                    .padding(3.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally

                                            ) {
                                                Image(
                                                    imageVector = FontAwesomeIcons.Solid.User,
                                                    contentDescription = "Account",
                                                    modifier = Modifier
                                                        .size(24.dp),
                                                    colorFilter = ColorFilter.tint(Color.White)
                                                )
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Text(
                                                    text = "Account",
                                                    color = Color.White,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        CurrentTab()
                    }
                },
                bottomBar = {
                    if (device == "Android" && !landscapeTablet) {
                        BottomNavigation(
                            backgroundColor = Color(53, 53, 93),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                items.forEachIndexed { index, item ->
                                    if (item != "Account") {
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
                                            modifier = Modifier.padding(2.dp).background(
                                                Color(53, 53, 93)
                                            ),
                                            selectedContentColor = Color(171, 171, 248),
                                            unselectedContentColor = Color.White
                                        )
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .height(92.dp)
                                                .width(92.dp)
                                                .padding(8.dp)
                                                .clip(
                                                    RoundedCornerShape(100)
                                                )
                                                .background(
                                                    if (tabNavigator.current == screens[index]) Color(
                                                        0xFFacacf9
                                                    ) else Color(0xFF473e5f)
                                                )
                                                .clickable {
                                                    tabNavigator.current = screens[index]
                                                }
                                                .padding(3.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally

                                        ) {
                                            Image(
                                                imageVector = FontAwesomeIcons.Solid.User,
                                                contentDescription = "Account",
                                                modifier = Modifier
                                                    .size(24.dp),
                                                colorFilter = ColorFilter.tint(Color.White)
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = "Account",
                                                color = Color.White,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}