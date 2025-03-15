import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.yousufjamil.accorm.Accorm
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Blog
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.EllipsisH
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.User
import database.AccormDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.HomeScreen
import screens.MoreItems
import screens.Services
import screens.device
import screens.landscapeTablet
import screens.Blogs
import screens.accounts.Dashboard
import screens.accounts.Login
import screens.accounts.Signup
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

        LaunchedEffect(Unit) {
            try {
                Accorm.Schema.migrate(
                    driver = AccormDatabase.driver,
                    oldVersion = 1,
                    newVersion = 2
                )
            } catch (_: Exception) {}
        }

        val screens = listOf(HomeScreen, Resources, Dashboard, Blogs, MoreItems)
        TabNavigator(
            tab = HomeScreen
        ) { tabNavigator ->
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
                                                    .shadow(
                                                        elevation = 5.dp,
                                                        shape = RoundedCornerShape(100)
                                                    )
                                                    .clip(
                                                        RoundedCornerShape(100)
                                                    )
                                                    .background(
                                                        brush =
                                                        if (tabNavigator.current != Dashboard && tabNavigator.current != Login && tabNavigator.current != Signup) {
                                                            Brush.radialGradient(
                                                                listOf(
                                                                    Color(0xFF5d5d7d),
                                                                    Color(0xFF49496d)
                                                                )
                                                            )
                                                        } else {
                                                            Brush.linearGradient(
                                                                listOf(
                                                                    Color(0xFF72728e),
                                                                    Color(0xFF72728e)
                                                                )
                                                            )
                                                        }
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
                        @Composable
                        fun Divider() {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(56.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(
                                                Color(53, 53, 93),
                                                Color(0xFF86869e),
                                                Color(53, 53, 93)
                                            )
                                        )
                                    )
                            )
                        }

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
                                            label = {
                                                Text(
                                                    text = item,
                                                    fontSize = 12.sp
                                                )
                                            },
                                            icon = {
                                                Icon(
                                                    imageVector = icons[index],
                                                    contentDescription = item,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                )
                                            },
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .background(
                                                    Color(53, 53, 93)
                                                ),
                                            selectedContentColor = Color(171, 171, 248),
                                            unselectedContentColor = Color.White
                                        )
                                        if (index != items.lastIndex) Divider()
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .height(92.dp)
                                                .width(92.dp)
                                                .padding(8.dp)
                                                .shadow(
                                                    elevation = 5.dp,
                                                    shape = RoundedCornerShape(100)
                                                )
                                                .clip(
                                                    RoundedCornerShape(100)
                                                )
                                                .background(
                                                    brush =
                                                    if (tabNavigator.current != Dashboard && tabNavigator.current != Login && tabNavigator.current != Signup) {
                                                        Brush.radialGradient(
                                                            listOf(
                                                                Color(0xFF5d5d7d),
                                                                Color(0xFF49496d)
                                                            )
                                                        )
                                                    } else {
                                                        Brush.linearGradient(
                                                            listOf(
                                                                Color(0xFF72728e),
                                                                Color(0xFF72728e)
                                                            )
                                                        )
                                                    }
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
                                        Divider()
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