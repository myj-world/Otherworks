package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowAltCircleDown
import compose.icons.fontawesomeicons.solid.ArrowAltCircleUp
import compose.icons.fontawesomeicons.solid.ArrowCircleDown
import compose.icons.fontawesomeicons.solid.ArrowCircleUp
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.InfoCircle
import compose.icons.fontawesomeicons.solid.Search
import kotlinx.coroutines.launch
import screens.device
import screens.landscapeTablet
import screens.poppins
import screens.resources.KnowPapersBySubject
import screens.resources.Paper

class PPQSearcher : Tab {
    private fun readResolve(): Any = PPQSearcher()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Search)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "PPQ Searcher",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        if (LoginStatus.getLoginStatus()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(31, 31, 54)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var keyword by remember { mutableStateOf("") }
                var rangeStart by remember { mutableStateOf("2017") }
                var rangeEnd by remember { mutableStateOf("2024") }
                var error by remember { mutableStateOf("") }
                var subject by remember { mutableStateOf("") }
                var level by remember { mutableStateOf("") }
                var subjectCode by remember { mutableStateOf("") }
                var component by remember { mutableStateOf("") }
                var variantCodes by remember { mutableStateOf(listOf<String>()) }

                var response by remember { mutableStateOf(listOf<PPQ>()) }
                var loading by remember { mutableStateOf(false) }
                var firstTimeTried by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()

                LazyVerticalGrid(
                    columns = if (device == "Android" && !landscapeTablet) GridCells.Fixed(1) else GridCells.Adaptive(
                        minSize = 300.dp
                    )
                ) {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        TextField(
                            value = keyword,
                            onValueChange = { keyword = it },
                            label = { Text("Keyword") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White
                            ),
                            singleLine = true,
                            enabled = !loading
                        )
                    }

                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = rangeStart,
                                onValueChange = { rangeStart = it },
                                label = { Text("Search years from") },
                                modifier = Modifier.weight(1f),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White
                                ),
                                singleLine = true,
                                enabled = !loading
                            )
                            TextField(
                                value = rangeEnd,
                                onValueChange = { rangeEnd = it },
                                label = { Text("Search years to") },
                                modifier = Modifier.weight(1f),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White
                                ),
                                singleLine = true,
                                enabled = !loading
                            )
                        }
                    }

                    item {
                        var menuExpanded by remember { mutableStateOf(false) }
                        var textFieldSize by remember { mutableStateOf(Size.Zero) }

                        val icon =
                            if (menuExpanded) rememberVectorPainter(FontAwesomeIcons.Solid.ArrowAltCircleUp) else rememberVectorPainter(
                                FontAwesomeIcons.Solid.ArrowAltCircleDown
                            )

                        TextField(
                            value = level,
                            onValueChange = {
                                level = it
                                subject = ""
                            },
                            label = { Text("Level") },
                            modifier = Modifier
                                .weight(1f)
                                .onGloballyPositioned {
                                    textFieldSize = it.size.toSize()
                                },
                            trailingIcon = {
                                Icon(
                                    painter = icon,
                                    contentDescription = "Open options",
                                    modifier = Modifier
                                        .clickable { menuExpanded = !menuExpanded }
                                        .size(30.dp),
                                    tint = Color.White
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White
                            ),
                            singleLine = true,
                            enabled = !loading
                        )


                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            },
                            modifier = Modifier
                                .background(Color(31, 31, 54))
                                .width(
                                    textFieldSize.width.dp
                                )
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    menuExpanded = false
                                    level = "IGCSE"
                                    subject = ""
                                }
                            ) {
                                Text(
                                    text = "IGCSE",
                                    color = Color.White,
                                    fontFamily = poppins
                                )
                            }

                            DropdownMenuItem(
                                onClick = {
                                    menuExpanded = false
                                    level = "AS"
                                    subject = ""
                                }
                            ) {
                                Text(
                                    text = "AS",
                                    color = Color.White,
                                    fontFamily = poppins
                                )
                            }

                            DropdownMenuItem(
                                onClick = {
                                    menuExpanded = false
                                    level = "A2"
                                    subject = ""
                                }
                            ) {
                                Text(
                                    text = "A2",
                                    color = Color.White,
                                    fontFamily = poppins
                                )
                            }
                        }
                    }

                    item {
                        var menuExpanded by remember { mutableStateOf(false) }
                        var textFieldSize by remember { mutableStateOf(Size.Zero) }

                        val icon =
                            if (menuExpanded) rememberVectorPainter(FontAwesomeIcons.Solid.ArrowAltCircleUp) else rememberVectorPainter(
                                FontAwesomeIcons.Solid.ArrowAltCircleDown
                            )

                        if (level != "") {
                            TextField(
                                value = subject,
                                onValueChange = { subject = it },
                                label = { Text("Subject") },
                                modifier = Modifier
                                    .weight(1f)
                                    .onGloballyPositioned {
                                        textFieldSize = it.size.toSize()
                                    },
                                trailingIcon = {
                                    Icon(
                                        painter = icon,
                                        contentDescription = "Open options",
                                        modifier = Modifier
                                            .clickable { menuExpanded = !menuExpanded }
                                            .size(30.dp),
                                        tint = Color.White
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White
                                ),
                                singleLine = true,
                                enabled = !loading
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            },
                            modifier = Modifier
                                .background(Color(31, 31, 54))
                                .width(
                                    textFieldSize.width.dp
                                )
                        ) {
                            if (level == "IGCSE") {
                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Accounting"
                                        subjectCode = "0452"
                                    }
                                ) {
                                    Text(
                                        text = "Accounting",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Biology"
                                        subjectCode = "0610"
                                    }
                                ) {
                                    Text(
                                        text = "Biology",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Chemistry"
                                        subjectCode = "0620"
                                    }
                                ) {
                                    Text(
                                        text = "Chemistry",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Physics"
                                        subjectCode = "0625"
                                    }
                                ) {
                                    Text(
                                        text = "Physics",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "CS"
                                        subjectCode = "0478"
                                    }
                                ) {
                                    Text(
                                        text = "CS",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Maths"
                                        subjectCode = "0580"
                                    }
                                ) {
                                    Text(
                                        text = "Maths",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "FLE"
                                        subjectCode = "0500"
                                    }
                                ) {
                                    Text(
                                        text = "FLE",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "ESL"
                                        subjectCode = "0510"
                                    }
                                ) {
                                    Text(
                                        text = "ESL",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Islamiyat"
                                        subjectCode = "0493"
                                    }
                                ) {
                                    Text(
                                        text = "Islamiyat",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }
                            } else {
                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Biology"
                                        subjectCode = "9700"
                                    }
                                ) {
                                    Text(
                                        text = "Biology",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Chemistry"
                                        subjectCode = "9701"
                                    }
                                ) {
                                    Text(
                                        text = "Chemistry",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Physics"
                                        subjectCode = "9702"
                                    }
                                ) {
                                    Text(
                                        text = "Physics",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "CS"
                                        subjectCode = "9618"
                                    }
                                ) {
                                    Text(
                                        text = "CS",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }

                                DropdownMenuItem(
                                    onClick = {
                                        menuExpanded = false
                                        subject = "Maths"
                                        subjectCode = "9709"
                                    }
                                ) {
                                    Text(
                                        text = "Maths",
                                        color = Color.White,
                                        fontFamily = poppins
                                    )
                                }
                            }
                        }
                    }

                    item {
                        TextField(
                            value = component,
                            onValueChange = { component = it },
                            label = { Text("Component") },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White
                            ),
                            singleLine = true,
                            enabled = !loading
                        )
                    }

                    item (
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row (
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Image(
                                imageVector = FontAwesomeIcons.Solid.InfoCircle,
                                contentDescription = "Info",
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = "Only enter paper number in component code. For example, to search Paper 1, only type \"1\" in component code (excluding double quotes, of course!).",
                                color = Color.Gray,
                                fontFamily = poppins,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        Button(
                            enabled = !loading,
                            onClick = {
                                try {
                                    error = ""
                                    response = emptyList()

                                    if (keyword.trim() == "") {
                                        error = "Keyword cannot be empty"
                                        throw Exception("Keyword cannot be empty")
                                    }

                                    val rangeStartInt: Int
                                    val rangeEndInt: Int

                                    rangeStart = rangeStart.trim()
                                    rangeEnd = rangeEnd.trim()

                                    try {
                                        rangeStartInt = rangeStart.toInt()
                                        rangeEndInt = rangeEnd.toInt()
                                    } catch (e: Exception) {
                                        error = "Invalid range"
                                        throw Exception("Invalid range")
                                    }

                                    if (rangeStartInt > rangeEndInt) {
                                        error = "Start year cannot be greater than end year"
                                        throw Exception("Start year cannot be greater than end year")
                                    }

                                    if (level != "IGCSE" && level != "AS" && level != "A2") {
                                        error = "Invalid level"
                                        throw Exception("Invalid level")
                                    }

                                    if (subject != "Biology" && subject != "Chemistry" && subject != "Physics" && subject != "CS" && subject != "Maths" && subject != "FLE" && subject != "ESL" && subject != "Islamiyat" && subject != "Accounting" && subject != "History" && subject != "Geography") {
                                        error = "Invalid subject"
                                        throw Exception("Invalid subject")
                                    }

                                    val componentInt: Int
                                    try {
                                        componentInt = component.toInt()
                                    } catch (e: Exception) {
                                        error = "Invalid component"
                                        throw Exception("Invalid component")
                                    }

                                    if (component.length != 1) {
                                        error = "Invalid component"
                                        throw Exception("Invalid component")
                                    }

                                    val tempPapersList = mutableListOf<String>()
                                    val tempvariantCodesList = mutableListOf<String>()

                                    val levelMod = if (level == "IGCSE") "IGCSE / O Level" else level

                                    for (i in KnowPapersBySubject(subject, levelMod)) {
                                        tempPapersList.add(i.first().toString())
                                        tempvariantCodesList.add(i.last().toString())
                                    }

                                    println(tempPapersList)

                                    if (component !in tempPapersList) {
                                        error = "Invalid component"
                                        throw Exception("Invalid component")
                                    }

                                    variantCodes = tempvariantCodesList

                                    coroutineScope.launch {
                                        firstTimeTried = true
                                        loading = true
                                        response = searchPPQs(
                                            range = rangeStartInt..rangeEndInt,
                                            subject = subject,
                                            level = level,
                                            subjectCode = subjectCode,
                                            component = componentInt,
                                            variantCodes = variantCodes,
                                            query = keyword
                                        )
                                        loading = false
                                    }
                                } catch (e: Exception) {
                                    println(e.message)
                                    error = e.message.toString()
                                    loading = false
                                    variantCodes = emptyList()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (loading) Color(31, 31, 54) else Color(118, 78, 255)
                            )
                        ) {
                            if (!loading) {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.Search,
                                    contentDescription = "Search",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }

                if (error != "") {
                    Text(
                        text = error,
                        color = Color.Red,
                        fontFamily = poppins,
                        textAlign = TextAlign.Center
                    )
                }

                if (!loading && firstTimeTried) {
                    Spacer(modifier = Modifier.height(50.dp))
                    val numFound = if (response.isEmpty()) "No results found" else if (response.size == 1) "1 result found" else "${response.size} results found"
                    Text(
                        text = numFound,
                        color = Color.White,
                        fontFamily = poppins,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(response.size) { i ->
                            val sessionMod = if (response[i].session == "s") "May/June" else "Oct/Nov"
                            val yearMod = "20${response[i].year}"

                            Paper(
                                title = "${response[i].subjectCode} ${response[i].subject} - Paper ${response[i].componentCode}",
                                session = sessionMod,
                                year = yearMod,
                                qpLink = response[i].qpLink,
                                msLink = response[i].msLink
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}