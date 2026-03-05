package screens.resources

import accounts.LoginStatus
import analytics.LogEvent
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Download
import compose.icons.fontawesomeicons.solid.ExternalLinkAlt
import compose.icons.fontawesomeicons.solid.Link
import compose.icons.fontawesomeicons.solid.Trash
import database.AccormDatabase
import database.DownloadsDataSource
import database.HistoryDataSource
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import network.RequestURL
import screens.accounts.Downloads
import screens.assets.CopyrightMessage
import screens.assets.contentType
import screens.lexend
import screens.poppins
import viewmodels.CurrentSubject
import screens.device
import screens.landscapeTablet
import viewmodels.ColourProvider
import kotlin.math.roundToInt

object Notes : Tab {
    private fun readResolve(): Any = Notes
    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u, title = "Notes", icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("Load Notes ${CurrentSubject.getSubject()}", null, null)

        val navigator = LocalNavigator.currentOrThrow
        var subjectRetrieve by remember {
            mutableStateOf("")
        }
        var subjectCode by remember {
            mutableStateOf("")
        }
        var level by remember {
            mutableStateOf("")
        }
        level = CurrentSubject.getLevel()

        if (level == "IGCSE / O Level") {
            subjectRetrieve = when (CurrentSubject.getSubject()) {
                "Islamiyat" -> "islamiyat"
                "History" -> "history"
                "Geography" -> "geography"
                "Accounting" -> "accounting"
                "Physics" -> "physics"
                "Chemistry" -> "chemistry"
                "Biology" -> "biology"
                "CS" -> "cs"
                "FLE" -> "fle"
                "ESL" -> "esl"
                else -> "maths"
            }
            subjectCode = when (CurrentSubject.getSubject()) {
                "Islamiyat" -> "0493/2058"
                "History" -> "0448/2059"
                "Geography" -> "0448/2059"
                "Accounting" -> "0452/7077"
                "Physics" -> "0625/5054"
                "Chemistry" -> "0620/5070"
                "Biology" -> "0610/5090"
                "CS" -> "0478/2210"
                "FLE" -> "0500"
                "ESL" -> "0510"
                else -> "0580/4024"
            }

            println("Tests $subjectRetrieve $subjectCode")
        } else {
            subjectRetrieve = when (CurrentSubject.getSubject()) {
                "Physics" -> "physics"
                "Chemistry" -> "chemistry"
                "Biology" -> "biology"
                "CS" -> "cs"
                else -> "maths"
            }
            subjectCode = when (CurrentSubject.getSubject()) {
                "Physics" -> "9702"
                "Chemistry" -> "9701"
                "Biology" -> "9700"
                "CS" -> "9618"
                else -> "9709"
            }

            println("Tests $subjectRetrieve $subjectCode")
        }

        val coroutineScope = rememberCoroutineScope()
        var data by remember { mutableStateOf("") }
        coroutineScope.launch {
            val levelRetrieve = if (level == "IGCSE / O Level") "IGCSE" else level
            data =
                RequestURL("https://accorm.ginastic.co/300/notes/?access-id=65aea3e3e6184&subject=$subjectRetrieve&level=$levelRetrieve&appv=2")
                    ?: "{\"1\": {\"unique_id\": 000000000,\"title\": \"Sample Notes\",\"url\": \"https://myj.rf.gd/Assets/Accorm/Accorm%20error.pdf\",\"chapter\": \"miscellaneous\",\"publisher\": \"Accorm\",\"pub_type\": \"Admin\",\"logo\": \"A\",\"logo_bg\": \"#000000\", \"specification:\": \"Sample Notes\", \"author\": \"Accorm\", \"published\": \"12/12/2023\", \"description\": \"Sample Notes\"}, \"num-of-rows\": 1}"
            println("https://accorm.ginastic.co/300/notes/?access-id=65aea3e3e6184&subject=$subjectRetrieve&level=$levelRetrieve&appv=2")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val arrangement =
                if (device == "Android" && !landscapeTablet) Arrangement.Center else Arrangement.SpaceBetween
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.radialGradient(
                            listOf(
                                parseColor(ColourProvider.colour1),
                                parseColor(ColourProvider.colour2)
                            ),
                            radius = 1500f,
                            center = Offset(-0.5f, -0.5f)
                        )
                    )
                    .padding(50.dp),
                horizontalArrangement = arrangement,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val titleAlignment =
                    if (device == "Android" && !landscapeTablet) Alignment.CenterHorizontally else Alignment.Start
                Column(
                    horizontalAlignment = titleAlignment
                ) {
                    Text(
                        text = subjectCode,
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = CurrentSubject.getSubject(),
                        fontSize = if (device == "Android" && !landscapeTablet) 36.sp else 56.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                if (device != "Android" || landscapeTablet) {
                    Column(
                        modifier = Modifier
                            .width(125.dp)
                            .height(125.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(22, 22, 22, 204))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val board = "CAIE"
                        Text(
                            text = "Board",
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = parseColor(ColourProvider.colour2)
                        )
                        Text(
                            text = board,
                            fontSize = 32.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    navigator.pop()
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Change Subject",
                    color = parseColor(ColourProvider.colour1),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Column(
                        modifier = Modifier
                            .width(175.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(parseColor(ColourProvider.colour1))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Resources",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppins,
                            color = Color.White
                        )
                        Text(
                            text = "Notes",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 5.dp, start = 5.dp)
                                .clickable {
                                    navigator.pop()
                                    navigator.push(Notes)
                                }
                        )
                        Text(
                            text = "Videos",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 5.dp, start = 5.dp)
                                .clickable {
                                    navigator.pop()
                                    navigator.push(Videos)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .width(175.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(parseColor(ColourProvider.colour1))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Materials",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppins,
                            color = Color.White
                        )
                        Text(
                            text = "PPQs",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 5.dp, start = 5.dp)
                                .clickable {
                                    navigator.pop()
                                    navigator.push(PPQs)
                                }
                        )
                        Text(
                            text = "Syllabus",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 5.dp, start = 5.dp)
                                .clickable {
                                    navigator.pop()
                                    navigator.push(Syllabus)
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                var noInternet by remember { mutableStateOf(false) }

                if (!noInternet) {
                    if (data != "" && data != "no data available.") {

                        val itemList = MutableList(0) { Item() }
                        var numRows by remember { mutableIntStateOf(0) }

                        try {
                            println(data)
                            JsonReader(data.reader()).use { reader ->
                                reader.beginObject()
                                while (reader.hasNext()) {
                                    val token = reader.peek()
                                    if (token.equals(JsonToken.NAME)) {
                                        try {
                                            reader.nextName()
                                            reader.beginObject()
                                            try {
                                                reader.skipValue()
                                                val uniqueId = reader.nextInt()
                                                reader.skipValue()
                                                val title = reader.nextString()
                                                reader.skipValue()
                                                val url = reader.nextString()
                                                reader.skipValue()
                                                val chapter = reader.nextString()
                                                reader.skipValue()
                                                val publisher = reader.nextString()
                                                reader.skipValue()
                                                val pubType = reader.nextString()
                                                reader.skipValue()
                                                val logo = reader.nextString()
                                                reader.skipValue()
                                                val logoBg = reader.nextString()
                                                reader.skipValue()
                                                val published = reader.nextString()
                                                reader.skipValue()
                                                val description = reader.nextString()
                                                reader.skipValue()
                                                val specification = reader.nextString()
                                                reader.skipValue()
                                                val credit = reader.nextString()
                                                reader.skipValue()
                                                val creditUrl = reader.nextString()


                                                itemList.add(
                                                    Item(
                                                        uniqueId = uniqueId,
                                                        title = title,
                                                        url = url,
                                                        chapter = chapter,
                                                        publisher = publisher,
                                                        pubType = pubType,
                                                        logo = logo,
                                                        logoBg = logoBg,
                                                        specification = specification,
                                                        published = published,
                                                        description = description,
                                                        credit = credit,
                                                        creditUrl = creditUrl
                                                    )
                                                )
                                            } catch (e: Exception) {
                                                println(reader.peek())
                                            }
                                            reader.endObject()
                                        } catch (e: Exception) {
                                            numRows = reader.nextInt()
                                            reader.skipValue()
                                        }
                                    }
                                }
                                println(itemList)
                            }
                        } catch (_: Exception) {
                            numRows = 0
                            itemList.add(
                                Item(
                                    uniqueId = 0,
                                    title = "Sample Notes",
                                    url = "https://accorm.ginastic.co/700/IGCSE/islamiyat/The%20Quranic%20Passages_373776704.pdf",
                                    chapter = "miscellaneous",
                                    publisher = "Accorm",
                                    pubType = "Admin",
                                    logo = "A",
                                    logoBg = "#000000",
                                    specification = "Sample Notes",
                                    published = "12/12/2023",
                                    description = "Sample Notes"
                                )
                            )
                        }

                        val favs by remember {
                            mutableStateOf(mutableListOf<String>())
                        }

                        if (LoginStatus.getLoginStatus()) {
                            itemList.sortBy { it.chapter.toIntOrNull() }
                            val tempList1 = mutableListOf<Item>()
                            itemList.forEach {
                                tempList1.add(it)
                            }
                            val tempList2 = mutableListOf<Item>()
                            itemList.forEach {
                                tempList2.add(it)
                            }

                            itemList.sortBy { it.chapter }
                            val tempList3 = mutableListOf<Item>()
                            itemList.forEach {
                                tempList3.add(it)
                            }
                            val tempList4 = mutableListOf<Item>()
                            itemList.forEach {
                                tempList4.add(it)
                            }

                            tempList1.forEach {
                                val isChapNum = it.chapter.toIntOrNull()
                                if (isChapNum == null) {
                                    tempList2.remove(it)
                                }
                            }
                            tempList3.forEach {
                                val isChapNum = it.chapter.toIntOrNull()
                                if (isChapNum != null) {
                                    tempList4.remove(it)
                                }
                            }
                            tempList4.sortBy { it.chapter }
                            itemList.clear()
                            itemList.addAll(tempList4)
                            itemList.addAll(tempList2)

                            if (LoginStatus.getFavourites() != "") {
                                val favsStr = LoginStatus.getFavourites().trim().split(" ")
                                println(favsStr)
                                favsStr.forEach {
                                    val item = it.split("-")
                                    println(item)
                                    favs.add(item[1])
                                }
                            }
                        }

                        var previousChapter = ""
                        var removeItem = Item()

                        itemList.forEach { item ->
                            if (previousChapter != item.chapter && LoginStatus.getLoginStatus()) {
                                previousChapter = item.chapter
                                val isNumber = item.chapter.toIntOrNull()
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = if (isNumber == null) "${item.chapter.replaceFirstChar { it.uppercase() }} Resources" else "Chapter ${item.chapter}",
                                    fontSize = 20.sp,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            if (item.title != "Sample Notes") {
                                if (favs.contains(item.uniqueId.toString())) {
                                    DisplayNotesItem(
                                        subjectRetrieve = subjectRetrieve,
                                        uniqueId = item.uniqueId,
                                        logo = item.logo,
//                                        logoBg = item.logoBg,
                                        logoBg = ColourProvider.colour1,
                                        chapter = item.chapter,
                                        publisher = item.publisher,
                                        title = item.title,
                                        description = item.description,
                                        specification = item.specification,
                                        published = item.published,
                                        url = item.url,
                                        credit = item.credit,
                                        creditUrl = item.creditUrl,
                                        backgroundColor = Color(0xFFffb900),
                                        textColor = Color.Black,
                                        labelColor = Color(0xFF373120),
                                        logoTextColour = Color.Black,
                                        downloadIconColor = Color.Black,
                                        level = level
                                    )
                                } else {
                                    DisplayNotesItem(
                                        subjectRetrieve = subjectRetrieve,
                                        uniqueId = item.uniqueId,
                                        logo = item.logo,
//                                        logoBg = item.logoBg,
                                        logoBg = ColourProvider.colour1,
                                        chapter = item.chapter,
                                        publisher = item.publisher,
                                        title = item.title,
                                        description = item.description,
                                        specification = item.specification,
                                        published = item.published,
                                        url = item.url,
                                        credit = item.credit,
                                        creditUrl = item.creditUrl,
                                        level = level
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            } else {
                                removeItem = item
                            }
                        }

                        if (removeItem.uniqueId != 111111111) {
                            itemList.remove(removeItem)
                        }

                        if (itemList.isEmpty()) {
                            noInternet = true
                        }
                    } else if (data == "no data available.") {
                        Text(
                            text = "No Notes Uploaded Yet!",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    } else {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                }

                if (noInternet) {
                    Text(
                        text = "No Internet Connection!",
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                CopyrightMessage()
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Serializable
data class Item(
    @SerialName("unique_id") val uniqueId: Int = 111111111,
    @SerialName("title") val title: String = "No Title Provided",
    @SerialName("url") val url: String = "https://accorm.ginastic.co/700/IGCSE/islamiyat/The%20Quranic%20Passages_373776704.pdf",
    @SerialName("chapter") val chapter: String = "Chapter Not Provided",
    @SerialName("publisher") val publisher: String = "Unknown",
    @SerialName("pub_type") val pubType: String = "Unknown",
    @SerialName("logo") val logo: String = "U",
    @SerialName("logo_bg") val logoBg: String = "#acacf9",
    @SerialName("specification") val specification: String = "Specification Not Provided",
    @SerialName("published") val published: String = "Unknown",
    @SerialName("description") val description: String = "Desciption Not Provided",
    @SerialName("credit") val credit: String = "Accorm",
    @SerialName("author") val creditUrl: String = "https://accorm.ginastic.co"
)

@Composable
fun DisplayNotesItem(
    subjectRetrieve: String,
    level: String,
    uniqueId: Int,
    logo: String,
    logoBg: String,
    chapter: String,
    publisher: String,
    title: String,
    description: String,
    specification: String,
    published: String,
    url: String = "",
    credit: String,
    creditUrl: String,
    backgroundColor: Color = Color(28, 28, 28),
    textColor: Color = Color.White,
    labelColor: Color = Color.Gray,
    logoTextColour: Color = Color.White,
    downloadIconColor: Color = Color(0xFFacacf9),
    isDownload: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    val coroutineScope = rememberCoroutineScope()
    val logoColor = parseColor(logoBg)

    var downloadDeleteConfirm by remember { mutableStateOf(false) }
    var isDownloading by remember { mutableStateOf(false) }
    var isDownloaded by remember { mutableStateOf(false) }
    var blockUser by remember { mutableStateOf(false) }

    coroutineScope.launch {
        val db = AccormDatabase.database
        val download = DownloadsDataSource(db = db)
        isDownloaded = download.checkDownloadExists(uniqueId)
    }

    if (device == "Android" && !landscapeTablet) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .animateContentSize()
                .clickable { isExpanded = !isExpanded }.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(logoColor)
                            .size(30.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = logo,
                            color = logoTextColour,
                            fontFamily = poppins,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = publisher,
                        color = textColor,
                        fontFamily = poppins,
                        fontSize = 18.sp
                    )
                }
                if (isDownload) {
                    var showdeleteMsg by remember { mutableStateOf(false) }
                    Image(
                        imageVector = FontAwesomeIcons.Solid.Trash,
                        contentDescription = "Delete",
                        colorFilter = ColorFilter.tint(Color.Red),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                showdeleteMsg = true
                            }
                    )

                    if (showdeleteMsg) {
                        AlertDialog(
                            onDismissRequest = {
                                showdeleteMsg = false
                            },
                            title = {
                                Text(
                                    text = "Delete Download?",
                                    fontSize = 20.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF181829)
                                )
                            },
                            text = {
                                Text(
                                    text = "Are you sure you want to delete this download?",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF1f1f36)
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            deleteDownload(uniqueid = uniqueId)
                                        }
                                        downloadDeleteConfirm = false
                                        showdeleteMsg = false
                                        blockUser = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFffffff)
                                    )
                                ) {
                                    Text(
                                        text = "Yes",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFF1f1f36)
                                    )
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        showdeleteMsg = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF1f1f36)
                                    )
                                ) {
                                    Text(
                                        text = "No",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFFffffff)
                                    )
                                }
                            },
                            backgroundColor = Color.White
                        )
                    }
                }
            }
            Text(
                text = title,
                fontFamily = poppins,
                fontSize = 22.sp,
                color = textColor
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    color = labelColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Specification",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = specification,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Chapter/Section/Paper",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = chapter,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Author",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = credit,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor,
                    modifier = Modifier
                        .clickable {
                            CurrentSubject.setUrl(creditUrl)
                            navigator.push(DisplayResourceExternal())
                        },
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Published on",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = published,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    var shouldCopy by remember { mutableStateOf(false) }
                    var msg by remember { mutableStateOf("") }
                    Button(
                        onClick = {
                            msg = "Link Copied to clipboard"
                            shouldCopy = true
                        },
                        modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = logoTextColour
                        )
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.Link,
                            contentDescription = "Copy Link",
                            colorFilter = ColorFilter.tint(logoColor),
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    var show by remember { mutableStateOf(false) }

                    if (shouldCopy) {
                        val shareUrl by remember {
                            mutableStateOf(
                                "https://accorm.ginastic.co/view/n/?s=$subjectRetrieve&id=$uniqueId"
                            )
                        }

                        Copy(shareUrl)
                        show = true
                        shouldCopy = false
                    }

                    if (show) {
                        AlertDialog(
                            onDismissRequest = {
                                show = false
                            },
                            title = {
                                Text(
                                    text = if (msg == "Link Copied to clipboard") "Copied to clipboard" else if (msg == "Download does not exist") "Error." else if (msg == "File downloaded. Please access from Account.") "Download Success" else "Download Error",
                                    fontSize = 20.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF181829)
                                )
                            },
                            text = {
                                Text(
                                    text = msg,
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF1f1f36)
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { show = false },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF1f1f36)
                                    )
                                ) {
                                    Text(
                                        text = if (msg == "Link Copied to clipboard") "Ready to share!" else if (msg == "Download does not exist") "Uh Oh. I'll re-download it." else if (msg == "File downloaded. Please access from Account.") "Will study from them!" else "OK",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFFffffff)
                                    )
                                }
                            },
                            backgroundColor = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    if (LoginStatus.getLoginStatus() && !isDownload && downloadIconColor != Color.Black) {
                        Button(
                            onClick = {
                                isDownloading = true
                                coroutineScope.launch {
                                    try {
                                        DownloadToInternal(
                                            uniqueid = uniqueId,
                                            title = title,
                                            subject = subjectRetrieve,
                                            type = "notes",
                                            publisher = publisher,
                                            publisherlogobg = logoBg,
                                            publisherlogo = logo,
                                            description = description,
                                            specification = specification,
                                            chapter = chapter,
                                            author = credit,
                                            authorcrediturl = creditUrl,
                                            publisheddate = published,
                                            link = url
                                        )
                                        msg = "File downloaded. Please access from Account."
                                        show = true
                                        isDownloaded = true
                                        isDownloading = false
                                    } catch (e: Exception) {
                                        msg = e.message.toString()
                                        show = true
                                        println(e.message)
                                        isDownloading = false
                                    }
                                }
//                            coroutineScope.launch {
//                                downloadFile(title = title, url = url)
//                                msg = "File saved to downloads"
//                                show = true
//                            }
                            },
                            modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            ),
                            enabled = !isDownloaded && !isDownloading
                        ) {
                            if (!isDownloaded && !isDownloading) {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.Download,
                                    contentDescription = "Download",
                                    colorFilter = ColorFilter.tint(downloadIconColor),
                                    modifier = Modifier.size(15.dp)
                                )
                            } else if (isDownloading) {
                                CircularProgressIndicator(color = Color.White)
                            } else {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.CheckCircle,
                                    contentDescription = "Download",
                                    colorFilter = ColorFilter.tint(downloadIconColor),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    var reportAnalytics by remember { mutableStateOf(false) }
                    if (reportAnalytics) {
                        LogEvent(
                            "Open notes $uniqueId ${CurrentSubject.getSubject()}",
                            uniqueId,
                            CurrentSubject.getSubject()
                        )
                        reportAnalytics = false
                    }
                    Button(
                        onClick = {
                            if (isDownload) {
                                coroutineScope.launch {
                                    try {
                                        launchDownload(navigator = navigator, uniqueid = uniqueId)
                                    } catch (e: Exception) {
                                        msg = e.message.toString()
                                        show = true
                                        println(e.message)
                                    }
                                }
                            } else {
                                CurrentSubject.setUrl(url)
                                CurrentSubject.setUrlFileName(title)
                                navigator.push(DisplayResourcePDF())
                            }
                            reportAnalytics = true

                            val modTextColor = textColor.toHex()
                            val modLabelColor = labelColor.toHex()
                            val modLogoTextColour = logoTextColour.toHex()
                            val modBackgroundColor = backgroundColor.toHex()
                            val modDownloadIconColor = downloadIconColor.toHex()

                            HistoryDataSource(AccormDatabase.database).addToHistory(
                                uniqueId = uniqueId.toString(),
                                title = title,
                                url = url,
                                chapter = chapter,
                                publisher = publisher,
                                logo = logo,
                                logoBg = logoBg,
                                specification = specification,
                                published = published,
                                description = description,
                                credit = credit,
                                creditUrl = creditUrl,
                                textColor = modTextColor,
                                labelColor = modLabelColor,
                                logoTextColour = modLogoTextColour,
                                backgroundColor = modBackgroundColor,
                                contentType = contentType.NOTE.toString(),
                                subjectRetrieve = subjectRetrieve,
                                source = "",
                                isDownload = isDownload,
                                downloadIconColor = modDownloadIconColor,
                                level = level
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = logoColor
                        )
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.ExternalLinkAlt,
                            contentDescription = "Open",
                            colorFilter = ColorFilter.tint(logoTextColour),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Open",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = logoTextColour
                        )
                    }
                }
            }
        }
    } else {
        var showdeleteMsg by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .width(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .animateContentSize()
                .clickable {
                    isExpanded = !isExpanded
                    if (isDownload) {
                        showdeleteMsg = true
                    }
                }.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(logoColor)
                            .size(30.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = logo,
                            color = logoTextColour,
                            fontFamily = poppins,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = publisher,
                        color = textColor,
                        fontFamily = poppins,
                        fontSize = 18.sp
                    )
                }
                if (isDownload) {
                    Text(
                        text = "🗑️",
                        color = Color.Red,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { showdeleteMsg = true }
                    )

                    if (showdeleteMsg) {
                        AlertDialog(
                            onDismissRequest = {
                                showdeleteMsg = false
                            },
                            title = {
                                Text(
                                    text = "Delete Download?",
                                    fontSize = 20.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF181829)
                                )
                            },
                            text = {
                                Text(
                                    text = "Are you sure you want to delete this download?",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF1f1f36)
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            deleteDownload(uniqueid = uniqueId)
                                        }
                                        downloadDeleteConfirm = false
                                        showdeleteMsg = false
                                        blockUser = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFffffff)
                                    )
                                ) {
                                    Text(
                                        text = "Yes",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFF1f1f36)
                                    )
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        showdeleteMsg = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF1f1f36)
                                    )
                                ) {
                                    Text(
                                        text = "No",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFFffffff)
                                    )
                                }
                            },
                            backgroundColor = Color.White
                        )
                    }
                }
            }
            Text(
                text = title,
                fontFamily = poppins,
                fontSize = 24.sp,
                color = textColor
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    color = labelColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Specification",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = specification,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Chapter/Section/Paper",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = chapter,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Author",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = credit,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor,
                    modifier = Modifier
                        .clickable {
                            CurrentSubject.setUrl(creditUrl)
                            navigator.push(DisplayResourceExternal())
                        },
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Published on",
                    fontSize = 12.sp,
                    fontFamily = lexend,
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = published,
                    fontSize = 15.sp,
                    fontFamily = poppins,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    var shouldCopy by remember { mutableStateOf(false) }
                    var msg by remember { mutableStateOf("") }
                    Button(
                        onClick = {
                            msg = "Link Copied to clipboard"
                            shouldCopy = true
                        },
                        modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = logoTextColour
                        )
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.Link,
                            contentDescription = "Copy Link",
                            colorFilter = ColorFilter.tint(backgroundColor),
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    var show by remember { mutableStateOf(false) }

                    if (shouldCopy) {
                        val shareUrl by remember {
                            mutableStateOf(
                                "https://accorm.ginastic.co/view/n/?s=$subjectRetrieve&id=$uniqueId"
                            )
                        }

                        Copy(shareUrl)
                        show = true
                        shouldCopy = false
                    }

                    if (show) {
                        AlertDialog(
                            onDismissRequest = {
                                show = false
                            },
                            title = {
                                Text(
                                    text = if (msg == "Link Copied to clipboard") "Copied to clipboard" else if (msg == "Download does not exist") "Error." else if (msg == "File downloaded. Please access from Account.") "Download Success" else "Download Error",
                                    fontSize = 20.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF181829)
                                )
                            },
                            text = {
                                Text(
                                    text = msg,
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFF1f1f36)
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { show = false },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF1f1f36)
                                    )
                                ) {
                                    Text(
                                        text = if (msg == "Link Copied to clipboard") "Ready to share!" else if (msg == "Download does not exist") "Uh Oh. I'll re-download it." else if (msg == "File downloaded. Please access from Account.") "Will study from them!" else "OK",
                                        fontSize = 18.sp,
                                        fontFamily = poppins,
                                        color = Color(0xFFffffff)
                                    )
                                }
                            },
                            backgroundColor = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    if (LoginStatus.getLoginStatus() && !isDownload && downloadIconColor != Color.Black) {
                        Button(
                            onClick = {
                                isDownloading = true
                                coroutineScope.launch {
                                    try {
                                        DownloadToInternal(
                                            uniqueid = uniqueId,
                                            title = title,
                                            subject = subjectRetrieve,
                                            type = "notes",
                                            publisher = publisher,
                                            publisherlogobg = logoBg,
                                            publisherlogo = logo,
                                            description = description,
                                            specification = specification,
                                            chapter = chapter,
                                            author = credit,
                                            authorcrediturl = creditUrl,
                                            publisheddate = published,
                                            link = url
                                        )
                                        msg = "File downloaded. Please access from Account."
                                        show = true
                                        isDownloaded = true
                                        isDownloading = false
                                    } catch (e: Exception) {
                                        msg = e.message.toString()
                                        show = true
                                        println(e.message)
                                        isDownloading = false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            ),
                            enabled = !isDownloaded && !isDownloading
                        ) {
                            if (!isDownloaded && !isDownloading) {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.Download,
                                    contentDescription = "Download",
                                    colorFilter = ColorFilter.tint(downloadIconColor),
                                    modifier = Modifier.size(15.dp)
                                )
                            } else if (isDownloading) {
                                CircularProgressIndicator(color = Color.White)
                            } else {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.CheckCircle,
                                    contentDescription = "Download",
                                    colorFilter = ColorFilter.tint(downloadIconColor),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }

//                        Button(
//                            onClick = {
//                                coroutineScope.launch {
//                                    downloadFile(title = title, url = url)
//                                    msg = "File saved to downloads"
//                                    show = true
//                                }
//                            },
//                            modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = logoTextColour
//                            )
//                        ) {
//                            Image(
//                                imageVector = FontAwesomeIcons.Solid.Download,
//                                contentDescription = "Download",
//                                colorFilter = ColorFilter.tint(logoColor),
//                                modifier = Modifier.size(15.dp)
//                            )
//                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    var reportAnalytics by remember { mutableStateOf(false) }
                    if (reportAnalytics) {
                        LogEvent(
                            "Open notes $uniqueId ${CurrentSubject.getSubject()}",
                            uniqueId,
                            CurrentSubject.getSubject()
                        )
                        reportAnalytics = false
                    }
                    Button(
                        onClick = {
                            if (isDownload) {
                                coroutineScope.launch {
                                    try {
                                        launchDownload(navigator = navigator, uniqueid = uniqueId)
                                    } catch (e: Exception) {
                                        msg = e.message.toString()
                                        show = true
                                        println(e.message)
                                    }
                                }
                            } else {
                                CurrentSubject.setUrl(url)
                                CurrentSubject.setUrlFileName(title)
                                navigator.push(DisplayResourcePDF())
                            }
                            reportAnalytics = true
                            val modTextColor = textColor.toHex()
                            val modLabelColor = labelColor.toHex()
                            val modLogoTextColour = logoTextColour.toHex()
                            val modBackgroundColor = backgroundColor.toHex()
                            val modDownloadIconColor = downloadIconColor.toHex()

                            HistoryDataSource(AccormDatabase.database).addToHistory(
                                uniqueId = uniqueId.toString(),
                                title = title,
                                url = url,
                                chapter = chapter,
                                publisher = publisher,
                                logo = logo,
                                logoBg = logoBg,
                                specification = specification,
                                published = published,
                                description = description,
                                credit = credit,
                                creditUrl = creditUrl,
                                textColor = modTextColor,
                                labelColor = modLabelColor,
                                logoTextColour = modLogoTextColour,
                                backgroundColor = modBackgroundColor,
                                contentType = contentType.NOTE.toString(),
                                subjectRetrieve = subjectRetrieve,
                                source = "",
                                isDownload = isDownload,
                                downloadIconColor = modDownloadIconColor,
                                level = level
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = logoColor
                        )
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.ExternalLinkAlt,
                            contentDescription = "Open",
                            colorFilter = ColorFilter.tint(logoTextColour),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Open",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = logoTextColour
                        )
                    }
                }
            }
        }
    }

    if (blockUser) {
//        AlertDialog(
//            onDismissRequest = {},
//            title = {
//                Text(
//                    text = "App Relaunch Required",
//                    fontSize = 20.sp,
//                    fontFamily = poppins,
//                    color = Color(0xFF181829)
//                )
//            },
//            text = {
//                Text(
//                    text = "Please relaunch the app",
//                    fontSize = 18.sp,
//                    fontFamily = poppins,
//                    color = Color(0xFF1f1f36)
//                )
//            },
//            confirmButton = {}
//        )
        navigator.pop()
        navigator.push(Downloads())
    }
}

fun Color.toHex(): String {
    val red = (this.red * 255).roundToInt()
    val green = (this.green * 255).roundToInt()
    val blue = (this.blue * 255).roundToInt()
    val alpha = (this.alpha * 255).roundToInt()

    return if (alpha == 255) {
        String.format("#%02X%02X%02X", red, green, blue) // RGB format
    } else {
        String.format("#%02X%02X%02X%02X", alpha, red, green, blue) // ARGB format
    }
}


@Composable
expect fun Copy(url: String)

expect fun parseColor(color: String): Color