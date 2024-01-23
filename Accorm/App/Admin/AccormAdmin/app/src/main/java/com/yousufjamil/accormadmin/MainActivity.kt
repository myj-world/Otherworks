package com.yousufjamil.accormadmin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.accormadmin.ui.theme.AccormAdminTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    lateinit var poppins: FontFamily
    lateinit var lexend: FontFamily

    @OptIn(ExperimentalMaterial3Api::class)
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
            AccormAdminTheme {
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
                                                    text = "Accorm Admin",
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

    @Composable
    fun Navigation(context: Context, navHostController: NavHostController) {
        NavHost(navHostController, "home") {
            composable("home") {
                HomeScreen(context)
            }
            composable("contributors") {
                ContributorsScreen(context = context)
            }
            composable("feedbacks") {
                FeedbacksScreen(context = context)
            }
            composable("accorm-blogs") {
                AccormBlogsScreen(context = context)
            }
            composable("upload") {
                UploadScreen(context = context)
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
                painterResource: Int = R.drawable.baseline_warning_24,
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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(75.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_ic),
                        contentDescription = "App icon",
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(corner = CornerSize(50.dp)))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Name",
                            fontFamily = lexend,
                            color = Color.White,
                            fontSize = 35.sp
                        )
                        Text(
                            text = "Email",
                            fontFamily = lexend,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
                Column {
                    Spacer(modifier = Modifier.height(40.dp))
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Log out",
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                NavSingleButton(
                    onClick = { navController.navigate("upload") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_upload_24,
                    contentDescription = "Upload"
                )
                NavSingleButton(
                    onClick = { navController.navigate("contributors") },
                    usesImageVector = true,
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Contributors"
                )
                NavSingleButton(
                    onClick = { navController.navigate("feedbacks") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_feedback_24,
                    contentDescription = "Feedbacks"
                )
                NavSingleButton(
                    onClick = { navController.navigate("accorm-blogs") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_newspaper_24,
                    contentDescription = "Accorm Blogs"
                )
                NavSingleButton(
                    onClick = { },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_folder_24,
                    contentDescription = "Resources"
                )
            }
        }
    }

    @Composable
    fun HomeScreen(context: Context) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(38, 38, 47, 255))
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(color = Color.White, fontFamily = lexend, fontSize = 60.sp)
                        ) {
                            append("Welcome to \n\n\n Ginastic\n\n\n")
                            withStyle(
                                SpanStyle(
                                    color = Color(144, 144, 214, 255)
                                )
                            ) {
                                append("Dashboard!\n\n\n\n")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "View, append & edit data according to your role. Choose an option from the Navigation bar to get started.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun UploadScreen(context: Context) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(38, 38, 47, 255))
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Upload",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "This section is specially made for content publishers of Accorm so they can upload resources hassle-free and with accorm's credit.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(29, 32, 54, 255))
                        .padding(20.dp)
                ) {
                    var title by remember {
                        mutableStateOf("")
                    }
                    var shortDes by remember {
                        mutableStateOf("")
                    }
                    var subject by remember {
                        mutableStateOf("")
                    }
                    var subjectSize by remember {
                        mutableStateOf(Size.Zero)
                    }
                    var subjectExpanded by remember {
                        mutableStateOf(false)
                    }

                    val subjectIcon = if (subjectExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    val subjects = listOf(
                        "Islamiyat (0493)",
                        "Pakistan Studies, History (0448/01)",
                        "Pakistan Studies, Geography (0448/02)",
                        "Maths (0580)",
                        "Physics (0625)",
                        "Chemistry (0620)",
                        "FLE (0500)",
                        "ESL (0510)",
                        "Computer Science (0478)",
                        "Biology (0610)",
                        "Accounting (0452)"
                    )

                    var chapter by remember {
                        mutableStateOf("")
                    }
                    var author by remember {
                        mutableStateOf("")
                    }
                    var customAuthorName by remember {
                        mutableStateOf("")
                    }
                    var customAuthorWebsite by remember {
                        mutableStateOf("")
                    }

                    TextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text(text = "Title")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = shortDes,
                        onValueChange = {
                            shortDes = it
                        },
                        label = {
                            Text(text = "Short Description")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = subject,
                        onValueChange = {
                            subject = it
                        },
                        label = {
                            Text(text = "Subject")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                subjectSize = coordinates.size.toSize()
                            },
                        colors = TextFieldDefaults.colors(),
                        trailingIcon = {
                            Icon(subjectIcon, "contentDescription",
                                Modifier.clickable { subjectExpanded = !subjectExpanded })
                        }
                    )
                    DropdownMenu(
                        expanded = subjectExpanded,
                        onDismissRequest = { subjectExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { subjectSize.width.toDp() })
                    ) {
                        subjects.forEach { label ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = label)
                                }, onClick = {
                                    subject = label
                                    subjectExpanded = false
                                }
                            )
                        }
                    }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(68, 75, 113, 255))
                            .padding(10.dp)
                    ) {
                        Text(text = "Credits", color = Color.White)
                    }
                }
            }
        }
    }

    @Composable
    fun ContributorsScreen(context: Context) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(38, 38, 47, 255))
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Contributors",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "See the list of Accorm Contributors, the users who have published and provided educational resources to Accorm.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_warning_24),
                        contentDescription = "Warning Icon",
                        tint = Color.Yellow
                    )
                    Text(
                        text = "Under Development.",
                        color = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun FeedbacksScreen(context: Context) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(38, 38, 47, 255))
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Feedbacks",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "Find here the feedbacks by users of Accorm to be updated of any bad user-experience, bugs, suggestions, encouragements, or simply praises.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_warning_24),
                        contentDescription = "Warning Icon",
                        tint = Color.Yellow
                    )
                    Text(
                        text = "Under Development.",
                        color = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun AccormBlogsScreen(context: Context) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(38, 38, 47, 255))
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Accorm \n \n\n Blogs",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "ONLY Educational category blogs are facilitated by Accorm. So find here the different statuses of blogs for verification purposes.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_warning_24),
                        contentDescription = "Warning Icon",
                        tint = Color.Yellow
                    )
                    Text(
                        text = "Under Development.",
                        color = Color.White
                    )
                }
            }
        }
    }
}