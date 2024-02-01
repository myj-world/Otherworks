package com.yousufjamil.accormadmin

import android.content.Context
import android.os.Bundle
import android.os.Handler
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
    lateinit var resourcesChosenSubject: String

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
            composable("upload-notes") {
                UploadNotesScreen(context = context)
            }
            composable("upload-vids") {
                UploadVideosScreen(context = context)
            }
            composable("accorm-videos") {
                AccormVideosScreen(context = context)
            }
            composable("resources") {
                Resources(context = context)
            }
            composable("resources-subject") {
                ResourcesSubject(context = context)
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
                    onClick = { navController.navigate("upload-notes") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_upload_24,
                    contentDescription = "Upload Notes"
                )
                NavSingleButton(
                    onClick = { navController.navigate("upload-vids") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_video_library_24,
                    contentDescription = "Upload Video"
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
                    onClick = { navController.navigate("accorm-videos") },
                    usesImageVector = false,
                    painterResource = R.drawable.baseline_video_settings_24,
                    contentDescription = "Accorm Videos"
                )
                NavSingleButton(
                    onClick = { navController.navigate("resources") },
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
    fun UploadNotesScreen(context: Context) {
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
                    text = "Upload \n \n \n Notes",
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
                        "Computer Science (0478)",
                        "Biology (0610)",
                        "Accounting (0452)"
                    )

                    var chapter by remember {
                        mutableStateOf("")
                    }
                    var chapterSize by remember {
                        mutableStateOf(Size.Zero)
                    }
                    var chapterExpanded by remember {
                        mutableStateOf(false)
                    }
                    val chapterIcon = if (subjectExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    var chapterLimit by remember {
                        mutableStateOf(0)
                    }
                    var papers by remember {
                        mutableStateOf(mutableListOf<String>())
                    }

                    var chapters by remember {
                        mutableStateOf(mutableListOf<String>())
                    }

                    var author by remember {
                        mutableStateOf("")
                    }
                    var uploaderAuthor by remember {
                        mutableStateOf(false)
                    }
                    var unknownAuthor by remember {
                        mutableStateOf(false)
                    }
                    var externalAuthor by remember {
                        mutableStateOf(false)
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
                            Icon(subjectIcon, "Pick a subject",
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

                                    chapterLimit = when (label) {
                                        "Islamiyat (0493)" -> 8
                                        "Pakistan Studies, History (0448/01)" -> 16
                                        "Pakistan Studies, Geography (0448/02)" -> 8
                                        "Maths (0580)" -> 24
                                        "Physics (0625)" -> 6
                                        "Chemistry (0620)" -> 12
                                        "Computer Science (0478)" -> 10
                                        "Biology (0610)" -> 21
                                        "Accounting (0452)" -> 7
                                        else -> 0
                                    }

                                    chapters = mutableListOf()

                                    if (chapterLimit > 0) {
                                        for (i in 1..chapterLimit) {
                                            chapters.add("Chapter $i")
                                        }
                                    }

                                    when (label) {
                                        "Islamiyat (0493)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")

                                        "Pakistan Studies, History (0448/01)" -> papers =
                                            mutableListOf("Paper 1")

                                        "Pakistan Studies, Geography (0448/02)" -> papers =
                                            mutableListOf("Paper 2")

                                        "Maths (0580)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4")

                                        "Physics (0625)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Chemistry (0620)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Computer Science (0478)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")

                                        "Biology (0610)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Accounting (0452)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")
                                    }

                                    for (i in 0..<papers.size) {
                                        chapters.add(papers[i])
                                    }
                                    chapters.add("Miscellaneous")
                                    chapters.add("All Chapters")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    if (subject != "") {
                        TextField(
                            value = chapter,
                            onValueChange = {
                                chapter = it
                            },
                            label = {
                                Text(text = "Chapter")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    chapterSize = coordinates.size.toSize()
                                },
                            colors = TextFieldDefaults.colors(),
                            trailingIcon = {
                                Icon(chapterIcon, "Pick a chapter",
                                    Modifier.clickable { chapterExpanded = !chapterExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = chapterExpanded,
                            onDismissRequest = { chapterExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { chapterSize.width.toDp() })
                        ) {
                            chapters.forEach { label ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = label)
                                    }, onClick = {
                                        chapter = label
                                        chapterExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(68, 75, 113, 255))
                            .padding(10.dp)
                    ) {
                        Text(text = "Credits", color = Color.White)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uploaderAuthor,
                                onClick = {
                                    uploaderAuthor = true
                                    unknownAuthor = false
                                    externalAuthor = false
                                    customAuthorName = ""
                                    customAuthorWebsite = ""
                                    author = "personal"
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.White,
                                    selectedColor = Color(118, 118, 118, 255)
                                )
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "I am the author",
                                color = Color.White
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = unknownAuthor,
                                onClick = {
                                    uploaderAuthor = false
                                    unknownAuthor = true
                                    externalAuthor = false
                                    customAuthorName = ""
                                    customAuthorWebsite = ""
                                    author = "unknown"
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.White,
                                    selectedColor = Color(118, 118, 118, 255)
                                )
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Unknown author",
                                color = Color.White
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = externalAuthor,
                                onClick = {
                                    uploaderAuthor = false
                                    unknownAuthor = false
                                    externalAuthor = true
                                    author = "external"
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.White,
                                    selectedColor = Color(118, 118, 118, 255)
                                )
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "External author",
                                color = Color.White
                            )
                        }

                        if (externalAuthor) {
                            TextField(
                                value = customAuthorName,
                                onValueChange = {
                                    customAuthorName = it
                                },
                                label = {
                                    Text(text = "Author name/Website name")
                                },
                                colors = TextFieldDefaults.colors()
                            )

                            TextField(
                                value = customAuthorWebsite,
                                onValueChange = {
                                    customAuthorWebsite = it
                                },
                                label = {
                                    Text(text = "Website link")
                                },
                                colors = TextFieldDefaults.colors()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            Text(text = "Choose file")
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "File Name")
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Only PDF file is acceptable. Max file size is up to 30MB.",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Text(text = "Upload")
                    }
                }
            }
        }
    }

    @Composable
    fun UploadVideosScreen(context: Context) {
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
                    text = "Upload \n \n \n Videos",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "Upload the course videos from here under the title of Accorm.",
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
                        "Computer Science (0478)",
                        "Biology (0610)",
                        "Accounting (0452)"
                    )

                    var chapter by remember {
                        mutableStateOf("")
                    }
                    var chapterSize by remember {
                        mutableStateOf(Size.Zero)
                    }
                    var chapterExpanded by remember {
                        mutableStateOf(false)
                    }
                    val chapterIcon = if (subjectExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    var chapterLimit by remember {
                        mutableStateOf(0)
                    }
                    var papers by remember {
                        mutableStateOf(mutableListOf<String>())
                    }

                    var chapters by remember {
                        mutableStateOf(mutableListOf<String>())
                    }


                    var url by remember {
                        mutableStateOf("")
                    }
                    var embeddedCode by remember {
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
                                    chapterLimit = when (label) {
                                        "Islamiyat (0493)" -> 8
                                        "Pakistan Studies, History (0448/01)" -> 16
                                        "Pakistan Studies, Geography (0448/02)" -> 8
                                        "Maths (0580)" -> 24
                                        "Physics (0625)" -> 6
                                        "Chemistry (0620)" -> 12
                                        "Computer Science (0478)" -> 10
                                        "Biology (0610)" -> 21
                                        "Accounting (0452)" -> 7
                                        else -> 0
                                    }

                                    chapters = mutableListOf()

                                    if (chapterLimit > 0) {
                                        for (i in 1..chapterLimit) {
                                            chapters.add("Chapter $i")
                                        }
                                    }

                                    when (label) {
                                        "Islamiyat (0493)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")

                                        "Pakistan Studies, History (0448/01)" -> papers =
                                            mutableListOf("Paper 1")

                                        "Pakistan Studies, Geography (0448/02)" -> papers =
                                            mutableListOf("Paper 2")

                                        "Maths (0580)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4")

                                        "Physics (0625)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Chemistry (0620)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Computer Science (0478)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")

                                        "Biology (0610)" -> papers =
                                            mutableListOf("Paper 2", "Paper 4", "Paper 6")

                                        "Accounting (0452)" -> papers =
                                            mutableListOf("Paper 1", "Paper 2")
                                    }

                                    for (i in 0..<papers.size) {
                                        chapters.add(papers[i])
                                    }
                                    chapters.add("Miscellaneous")
                                    chapters.add("All Chapters")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    if (subject != "") {
                        TextField(
                            value = chapter,
                            onValueChange = {
                                chapter = it
                            },
                            label = {
                                Text(text = "Chapter")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    chapterSize = coordinates.size.toSize()
                                },
                            colors = TextFieldDefaults.colors(),
                            trailingIcon = {
                                Icon(chapterIcon, "Pick a chapter",
                                    Modifier.clickable { chapterExpanded = !chapterExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = chapterExpanded,
                            onDismissRequest = { chapterExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { chapterSize.width.toDp() })
                        ) {
                            chapters.forEach { label ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = label)
                                    }, onClick = {
                                        chapter = label
                                        chapterExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = url,
                        onValueChange = {
                            url = it
                        },
                        label = {
                            Text(text = "Video URL")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = embeddedCode,
                        onValueChange = {
                            embeddedCode = it
                        },
                        label = {
                            Text(text = "Video's embedded code")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Text(text = "Upload")
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

    @Composable
    fun AccormVideosScreen(context: Context) {
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
                    text = "Accorm \n \n\n Videos",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "Find here the uploaded videos by Accorm Team or Contributors.",
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
    fun Resources(context: Context) {
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
                    text = "Resources",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "Find here the uploaded resources by Accorm Team or Contributors. Click on any to view the subject-specific table.",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))

                @Composable
                fun SingleSubject(
                    subject: String
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(35, 36, 53, 255))
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = subject,
                            color = Color.White,
                            fontFamily = poppins
                        )
                        Button(
                            onClick = {
                                resourcesChosenSubject = subject
                                navController.navigate("resources-subject")
                            },
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            Text(text = "View")
                        }
                    }
                }

                SingleSubject(subject = "Islamiyat")
                SingleSubject(subject = "Pakistan Studies(History)")
                SingleSubject(subject = "Pakistan Studies(Geography)")
                SingleSubject(subject = "Accounting")
                SingleSubject(subject = "Physics")
                SingleSubject(subject = "Chemistry")
                SingleSubject(subject = "Maths")
                SingleSubject(subject = "Computer Science")
                SingleSubject(subject = "Biology")
            }
        }
    }

    @Composable
    fun ResourcesSubject(context: Context) {

        var canDecode by remember {
            mutableStateOf(false)
        }

        var result by remember {
            mutableStateOf("")
        }

        fun retrieveData(subject: String) {
            val bgWorker = BackgroundWorker(context)
            Thread {
                bgWorker.execute("https://accorm.ginastic.co/300/fetch/?access-id=65aea3e3e6184&subject=$subject")
            }.start()

            fun checkStatus() {
                Handler().postDelayed(
                    {
                        if (bgWorker.status.toString() == "FINISHED") {
                            canDecode = true
                            result = bgWorker.response
                        } else {
                            checkStatus()
                        }
                    }, 3000
                )
            }
            checkStatus()
        }
        retrieveData(resourcesChosenSubject.lowercase())

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
                    text = resourcesChosenSubject,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = lexend,
                    fontSize = 60.sp
                )
                Text(
                    text = "See here the $resourcesChosenSubject resources",
                    color = Color(144, 144, 214, 255),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate("resources")
                        },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Back"
                        )
                        Text(text = "Back")
                    }
                    Button(
                        onClick = {
                            navController.navigate("resources-subject")
                        },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_refresh_24),
                            contentDescription = "Refresh"
                        )
                        Text(text = "Refresh")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!canDecode) {
                    Text(
                        text = "Loading...",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 28.sp
                    )
                }
                if (canDecode) {
                    Text(
                        text = "URL: https://accorm.ginastic.co/300/fetch/?access-id=65aea3e3e6184&subject=${resourcesChosenSubject.lowercase()}",
                        color = Color.White
                    )
                    Text(
                        text = "Data: $result",
                        color = Color.White
                    )
                }
            }
        }
    }
}