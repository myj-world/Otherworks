package screens.resources

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
import compose.icons.fontawesomeicons.solid.ExternalLinkAlt
import compose.icons.fontawesomeicons.solid.Link
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import network.RequestURL
import screens.assets.CopyrightMessage
import screens.lexend
import screens.poppins
import viewmodels.CurrentSubject
import screens.device
import screens.landscapeTablet

object Notes : Tab {
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
                "Computer Science" -> "cs"
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
                "Computer Science" -> "0478/2210"
                "FLE" -> "0500"
                "ESL" -> "0510"
                else -> "0580/4024"
            }

            println("Tests $subjectRetrieve $subjectCode")
        } else {
            navigator.pop()
        }

        val coroutineScope = rememberCoroutineScope()
        var data by remember { mutableStateOf("") }
        coroutineScope.launch {
            data =
                RequestURL("https://accorm.ginastic.co/300/notes/?access-id=65aea3e3e6184&subject=$subjectRetrieve")
                    ?: "{\"1\": {\"unique_id\": 000000000,\"title\": \"Sample Notes\",\"url\": \"https://accorm.ginastic.co/700/IGCSE/islamiyat/The%20Quranic%20Passages_373776704.pdf\",\"chapter\": \"miscellaneous\",\"publisher\": \"Accorm\",\"pub_type\": \"Admin\",\"logo\": \"A\",\"logo_bg\": \"#000000\", \"specification:\": \"Sample Notes\", \"author\": \"Accorm\", \"published\": \"12/12/2023\", \"description\": \"Sample Notes\"}, \"num-of-rows\": 1}"
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
                                Color(106, 106, 193),
                                Color(153, 109, 194)
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
                            color = Color(172, 172, 249)
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
                    color = Color(172, 172, 249),
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
                            .background(Color(172, 172, 249))
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
                            .background(Color(172, 172, 249))
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
                @Composable
                fun Item(
                    logo: String,
                    logoColor: Color,
                    chapter: String,
                    publisher: String,
                    title: String,
                    description: String,
                    specification: String,
                    published: String,
                    url: String
                ) {
                    var isExpanded by remember { mutableStateOf(false) }

                    if (device == "Android" && !landscapeTablet) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(28, 28, 28))
                                .animateContentSize()
                                .clickable { isExpanded = !isExpanded }.padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                        color = Color.White,
                                        fontFamily = poppins,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = publisher,
                                    color = Color.White,
                                    fontFamily = poppins,
                                    fontSize = 18.sp
                                )
                            }
                            Text(
                                text = title,
                                fontFamily = poppins,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = description,
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Specification",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = specification,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Chapter/Section/Paper",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = chapter,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Published on",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = published,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row {
                                    var shouldCopy by remember { mutableStateOf(false) }
                                    Button(
                                        onClick = {
                                            shouldCopy = true
                                        },
                                        modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.White
                                        )
                                    ) {
                                        Image(
                                            imageVector = FontAwesomeIcons.Solid.Link,
                                            contentDescription = "Copy Link",
                                            colorFilter = ColorFilter.tint(Color(172, 172, 249)),
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }

                                    var show by remember { mutableStateOf(false) }

                                    if (shouldCopy) {
                                        Copy(url)
                                        show = true
                                        shouldCopy = false
                                    }

                                    if (show) {
                                        AlertDialog(onDismissRequest = { show = false },
                                            title = { Text("Link copied to clipboard") },
                                            confirmButton = {
                                                Button(onClick = { show = false }) {
                                                    Text("OK")
                                                }
                                            })
                                    }

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Button(
                                        onClick = {
                                            CurrentSubject.setUrl(url)
                                            navigator.push(DisplayResourcePDF())
                                        },
                                        modifier = Modifier.fillMaxWidth().height(45.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(172, 172, 249)
                                        )
                                    ) {
                                        Image(
                                            imageVector = FontAwesomeIcons.Solid.ExternalLinkAlt,
                                            contentDescription = "Open",
                                            colorFilter = ColorFilter.tint(Color.White),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "Open",
                                            fontSize = 20.sp,
                                            fontFamily = poppins,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .width(400.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(28, 28, 28))
                                .animateContentSize()
                                .clickable { isExpanded = !isExpanded }.padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                        color = Color.White,
                                        fontFamily = poppins,
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = publisher,
                                    color = Color.White,
                                    fontFamily = poppins,
                                    fontSize = 18.sp
                                )
                            }
                            Text(
                                text = title,
                                fontFamily = poppins,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = description,
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Specification",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = specification,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Chapter/Section/Paper",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = chapter,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Published on",
                                    fontSize = 12.sp,
                                    fontFamily = lexend,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = published,
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row {
                                    var shouldCopy by remember { mutableStateOf(false) }
                                    Button(
                                        onClick = {
                                            shouldCopy = true
                                        },
                                        modifier = Modifier.fillMaxWidth(0.2f).height(45.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.White
                                        )
                                    ) {
                                        Image(
                                            imageVector = FontAwesomeIcons.Solid.Link,
                                            contentDescription = "Copy Link",
                                            colorFilter = ColorFilter.tint(Color(172, 172, 249)),
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }

                                    var show by remember { mutableStateOf(false) }

                                    if (shouldCopy) {
                                        Copy(url)
                                        show = true
                                        shouldCopy = false
                                    }

                                    if (show) {
                                        AlertDialog(onDismissRequest = { show = false },
                                            title = { Text("Link copied to clipboard") },
                                            confirmButton = {
                                                Button(onClick = { show = false }) {
                                                    Text("OK")
                                                }
                                            })
                                    }

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Button(
                                        onClick = {
                                            CurrentSubject.setUrl(url)
                                            navigator.push(DisplayResourcePDF())
                                        },
                                        modifier = Modifier.fillMaxWidth().height(45.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(172, 172, 249)
                                        )
                                    ) {
                                        Image(
                                            imageVector = FontAwesomeIcons.Solid.ExternalLinkAlt,
                                            contentDescription = "Open",
                                            colorFilter = ColorFilter.tint(Color.White),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "Open",
                                            fontSize = 20.sp,
                                            fontFamily = poppins,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (data != "" && data != "no data available.") {

                    val itemList = MutableList(0) { Item() }
                    var numRows by remember { mutableIntStateOf(0) }

                    try {
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
                                                    description = description
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

                    itemList.removeAt(0)

                    itemList.forEach { item ->
                        Item(
                            logo = item.logo,
                            logoColor = parseColor(item.logoBg),
                            chapter = item.chapter,
                            publisher = item.publisher,
                            title = item.title,
                            description = item.description,
                            specification = item.specification,
                            published = item.published,
                            url = item.url
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }  else if (data == "no data available.") {
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
    @SerialName("description") val description: String = "Desciption Not Provided"
)


@Composable
expect fun Copy(url: String)

expect fun parseColor(color: String): Color