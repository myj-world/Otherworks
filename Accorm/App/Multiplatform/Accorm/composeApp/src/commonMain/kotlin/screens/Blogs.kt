package screens

import analytics.LogEvent
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
import androidx.compose.ui.text.style.TextAlign
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
import compose.icons.fontawesomeicons.solid.ExternalLinkAlt
import compose.icons.fontawesomeicons.solid.Link
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import network.RequestURL
import screens.assets.CopyrightMessage
import screens.resources.Copy
import screens.resources.DisplayResourceExternal
import viewmodels.CurrentSubject

object Blogs : Tab {
    private fun readResolve(): Any = Blogs
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Blogs",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("Load Blogs", null, null)

        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = rememberCoroutineScope()
        var data by remember { mutableStateOf("") }
        coroutineScope.launch {
//            val levelRetrieve = if (level == "IGCSE / O Level") "IGCSE" else level
            data =
                RequestURL("https://accorm.ginastic.co/300/blogs/?access-id=65c3ad4b976e3")
                    ?: "{\"1\": {\"unique_id\": 000000000,\"title\": \"Sample Notes\",\"publisher\": \"Accorm\",\"pub_type\": \"Admin\", \"published\": \"31-12-24 19:28:00\", \"description\": \"Sample Notes\"}, \"num-of-rows\": 1}"
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
                        text = "Accorm",
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Educational Blogs",
                        fontSize = if (device == "Android" && !landscapeTablet) 26.sp else 56.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                if (device != "Android" || landscapeTablet) {
                    Column(
                        modifier = Modifier
                            .width(325.dp)
                            .height(125.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(22, 22, 22, 204))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Designed for",
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(172, 172, 249)
                        )
                        Text(
                            text = "Education",
                            fontSize = 32.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                @Composable
                fun DisplayBlogItem(
                    uniqueId: Int,
                    publisher: String,
                    title: String,
                    description: String,
                    datePublished: String
                ) {
                    if (device == "Android" && !landscapeTablet) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(28, 28, 28))
                                .padding(20.dp),
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
                                        .background(Color(0xFFACACF9))
                                        .size(30.dp), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = publisher.substring(0, 1),
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
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = description,
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFF8891bb),
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var showDialog by remember { mutableStateOf(false) }
                                Text(
                                    text = "Published on: ${datePublished.substring(0, 9)}",
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFe5f5f6)
                                )
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.CheckCircle,
                                    contentDescription = "Checked & Verified",
                                    colorFilter = ColorFilter.tint(Color(172, 172, 249)),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { showDialog = true }
                                )
                                if (showDialog) {
                                    AlertDialog(
                                        onDismissRequest = {
                                            showDialog = false
                                        },
                                        title = {
                                            Text(
                                                text = "Checked & Verified",
                                                fontSize = 20.sp,
                                                fontFamily = poppins,
                                                color = Color(0xFF181829)
                                            )
                                        },
                                        text = {
                                            Text(
                                                text = "This blog has been checked & verified.",
                                                fontSize = 18.sp,
                                                fontFamily = poppins,
                                                color = Color(0xFF1f1f36)
                                            )
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = { showDialog = false },
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color(0xFF1f1f36)
                                                )
                                            ) {
                                                Text(
                                                    text = "That's great!",
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
                                    val shareUrl by remember {
                                        mutableStateOf(
                                            "https://blogs.ginastic.co/blog/?id=$uniqueId"
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
                                                text = "Copied to Clipboard",
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
                                                    text = "Read to share!",
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

                                Button(
                                    onClick = {
                                        CurrentSubject.setUrl(
                                            "https://blogs.ginastic.co/blog/?id=$uniqueId"
                                        )
                                        navigator.push(DisplayResourceExternal())
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
                                        text = "Open Blog",
                                        fontSize = 20.sp,
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .width(400.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(28, 28, 28))
                                .padding(20.dp),
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
                                        .background(Color(0xFFACACF9))
                                        .size(30.dp), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = publisher.substring(0, 1),
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
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = description,
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFF8891bb),
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var showDialog by remember { mutableStateOf(false) }
                                Text(
                                    text = "Published on: ${datePublished.substring(0, 9)}",
                                    fontSize = 15.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFe5f5f6)
                                )
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.CheckCircle,
                                    contentDescription = "Checked & Verified",
                                    colorFilter = ColorFilter.tint(Color(172, 172, 249)),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { showDialog = true }
                                )
                                if (showDialog) {
                                    AlertDialog(
                                        onDismissRequest = {
                                            showDialog = false
                                        },
                                        title = {
                                            Text(
                                                text = "Checked & Verified",
                                                fontSize = 20.sp,
                                                fontFamily = poppins,
                                                color = Color(0xFF181829)
                                            )
                                        },
                                        text = {
                                            Text(
                                                text = "This blog has been checked & verified.",
                                                fontSize = 18.sp,
                                                fontFamily = poppins,
                                                color = Color(0xFF1f1f36)
                                            )
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = { showDialog = false },
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color(0xFF1f1f36)
                                                )
                                            ) {
                                                Text(
                                                    text = "That's great!",
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
                                    val shareUrl by remember {
                                        mutableStateOf(
                                            "https://blogs.ginastic.co/blog/?id=$uniqueId"
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
                                                text = "Copied to Clipboard",
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
                                                    text = "Read to share!",
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

                                Button(
                                    onClick = {
                                        CurrentSubject.setUrl(
                                            "https://blogs.ginastic.co/blog/?id=$uniqueId"
                                        )
                                        navigator.push(DisplayResourceExternal())
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
                                        text = "Open Blog",
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

                if (data != "" && data != "no data available.") {

                    val itemList = MutableList(0) { BlogItem() }
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
                                            val publisher = reader.nextString()
                                            reader.skipValue()
                                            val pubType = reader.nextString()
                                            reader.skipValue()
                                            val description = reader.nextString()
                                            reader.skipValue()
                                            val published = reader.nextString()

                                            itemList.add(
                                                BlogItem(
                                                    uniqueId = uniqueId,
                                                    title = title,
                                                    publisher = publisher,
                                                    pubType = pubType,
                                                    description = description,
                                                    date = published
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
                            BlogItem(
                                uniqueId = 0,
                                title = "Sample Notes",
                                publisher = "Accorm",
                                pubType = "Admin",
                                date = "12/12/2023"
                            )
                        )
                    }

                    itemList.forEach { item ->
                        DisplayBlogItem(
                            uniqueId = item.uniqueId,
                            publisher = item.publisher,
                            title = item.title,
                            description = item.description,
                            datePublished = item.date
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (itemList.isEmpty()) {
                        Text(
                            text = "No Internet Connection!",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
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

                Spacer(modifier = Modifier.height(30.dp))
                CopyrightMessage()
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Serializable
data class BlogItem(
    @SerialName("unique_id") val uniqueId: Int = 111111111,
    @SerialName("title") val title: String = "No Title Provided",
    @SerialName("publisher") val publisher: String = "Unknown",
    @SerialName("pub_type") val pubType: String = "Unknown",
    @SerialName("description") val description: String = "Unknown",
    @SerialName("date") val date: String = "Unknown"
)