package com.yousufjamil.accorm

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.accorm.ui.theme.AccormTheme
import kotlinx.coroutines.launch

lateinit var navController: NavHostController
lateinit var poppins: FontFamily
lateinit var lexend: FontFamily

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        poppins = FontFamily(
            Font(R.font.poppins_thin, FontWeight.Thin),
            Font(R.font.poppins_light, FontWeight.Light),
            Font(R.font.poppins_bold, FontWeight.Bold),
            Font(R.font.poppins_semibold, FontWeight.SemiBold),
            Font(R.font.poppins_regular, FontWeight.Normal)
        )

        lexend = FontFamily(
            Font(R.font.lexend_thin, FontWeight.Thin),
            Font(R.font.lexend_regular, FontWeight.Normal),
            Font(R.font.lexend_bold, FontWeight.Bold)
        )

        setContent {
            AccormTheme {

                navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    NavigationDrawer() { scope.launch { drawerState.close() } }
                                }
                            }
                        }
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Scaffold(
                                modifier = Modifier.padding(0.dp),
                                topBar = {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        TopAppBar(
                                            title = {
                                                Text(
                                                    text = "Accorm",
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.95f)
                                                        .padding(top = 6.dp),
                                                    textAlign = TextAlign.Left,
                                                    fontFamily = poppins,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            },
                                            navigationIcon = {
                                                IconButton(
                                                    onClick = {
                                                        scope.launch { drawerState.open() }
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Menu,
                                                        contentDescription = "Menu Button",
                                                        modifier = Modifier.size(30.dp)
                                                    )
                                                }
                                            },
                                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                                navigationIconContentColor = Color.White,
                                                containerColor = Color(75, 75, 110, 255)
                                            )
                                        )
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(it),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Navigation(this@MainActivity, navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(context: Context, navHostController: NavHostController) {
    NavHost(navHostController, "home") {
        composable("home") {
            HomeScreen(context)
        }
        composable("about") {
            AboutUsScreen(context)
        }
    }
}

@Composable
fun NavigationDrawer(closeDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(46, 55, 72, 255))
            .padding(horizontal = 20.dp)
            .clickable { closeDrawer() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        @Composable
        fun Divider() {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(33, 41, 56, 255))
                    .height(2.dp)
            )
        }

        @Composable
        fun NavSingleButton(
            onClick: () -> Unit,
            usesImageVector: Boolean,
            imageVector: ImageVector = Icons.Default.Warning,
            painterResource: Int = R.drawable.app_ic,
            contentDescription: String
        ) {
            Button(
                onClick = {
                    closeDrawer()
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(corner = CornerSize(1.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (usesImageVector) {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(painterResource),
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = contentDescription,
                        fontFamily = lexend,
                        fontSize = 19.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_ic),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(50.dp)))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Accorm",
                fontFamily = lexend,
                color = Color.White,
                fontSize = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.book_open,
                contentDescription = "Subjects"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.apps,
                contentDescription = "Features"
            )
            NavSingleButton(
                onClick = { navController.navigate("about") },
                usesImageVector = true,
                imageVector = Icons.Default.Info,
                contentDescription = "Info"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            NavSingleButton(
                onClick = { },
                usesImageVector = true,
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Contribute"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = true,
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        IconButton(
            onClick = {
                closeDrawer()
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(172, 172, 249, 255)
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(100.dp)))
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wb_sunny),
                contentDescription = "Change Theme",
                tint = Color(33, 33, 43, 255),
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun HomeScreen(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(38, 38, 47, 255))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = Color.White, fontFamily = poppins, fontSize = 60.sp)
                ) {
                    append("Educate")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n\n")
                    }
                    append("Empower")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n\n")
                    }
                    append("Excel")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Where students and educational \ncontent blend",
            color = Color(144, 144, 214, 255),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {

            @Composable
            fun HomeIcon(
                onClick: () -> Unit,
                usesImageVector: Boolean,
                imageVector: ImageVector = Icons.Default.Warning,
                painterResource: Int = R.drawable.app_ic,
                contentDescription: String
            ) {
                IconButton(
                    onClick = { onClick() },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(33, 33, 43, 255)
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(100))
                ) {
                    if (usesImageVector) {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(painterResource),
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
            HomeIcon(
                onClick = { navController.navigate("about") },
                usesImageVector = true,
                imageVector = Icons.Default.Info,
                contentDescription = "Info"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = false,
                painterResource = R.drawable.book_open,
                contentDescription = "Subjects"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = false,
                painterResource = R.drawable.apps,
                contentDescription = "Features"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = true,
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Contribute"
            )
        }
    }
}

@Composable
fun AboutUsScreen(context: Context) {
    val scrollState = rememberScrollState()

    @Composable
    fun DeveloperBox(
        icon: Int,
        name: String,
        role: String,
        email: String
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(53, 53, 62, 255))
                .fillMaxWidth(0.8f)
                .height(280.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.9f)
                        .height(140.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = name,
                    color = Color(227, 227, 228, 255),
                    fontFamily = poppins,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = role,
                    color = Color(160, 160, 231, 255),
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .background(Color(75, 75, 110, 255))
                        .clickable {
                            val url = "mailto:$email"
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            context.startActivity(intent)
                        }
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(255, 255, 255, 255),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(38, 38, 47, 255))
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        DeveloperBox(
            icon = R.drawable.musab,
            name = "Musab Khan",
            role = "Project Manager",
            email = "contact@ginastic.co"
        )
        DeveloperBox(
            icon = R.drawable.abd,
            name = "Abdullah Umair",
            role = "Designer",
            email = "solution.i67@outlook.com"
        )
        DeveloperBox(
            icon = R.drawable.yousuf,
            name = "M.Yousuf Jamil",
            role = "Project Manager",
            email = "contact.us.myj@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.majid,
            name = "Majid Muhammad",
            role = "Content Provider",
            email = "mylifechoice96@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.faizan,
            name = "Faizan",
            role = "Content Provider",
            email = "adamcroft715@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.basim,
            name = "Basim Kashif",
            role = "Contributor",
            email = "bkashamsi2008@gmail.com"
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
