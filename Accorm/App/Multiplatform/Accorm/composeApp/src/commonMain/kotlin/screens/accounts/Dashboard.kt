package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.gson.stream.JsonReader
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SignOutAlt
import compose.icons.fontawesomeicons.solid.User
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import network.RequestURL
import screens.assets.CopyrightMessage
import screens.device
import screens.landscapeTablet
import screens.poppins
import screens.resources.DisplayNotesItem
import screens.resources.DisplayVideosItem
import screens.resources.parseColor

object Dashboard : Tab {
    private fun readResolve(): Any = Dashboard
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.User)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Dashboard",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        var response by remember { mutableStateOf("") }
        var refreshFavs by remember { mutableIntStateOf(0) }

        suspend fun retrieveFromUrl(
            subject: String,
            url: String,
            itemToFind: String,
            type: String
        ): FavouriteItem {
            println("$subject $url $itemToFind")

            response = RequestURL(url) ?: ""
            println(response)

            return if (response != "false") {

                var item = FavouriteItem(
                    subject = "",
                    type = "",
                    id = "0",
                    publisherLogo = "",
                    publisherLogoBg = "",
                    publisherName = "",
                    name = "",
                    description = "",
                    specification = "",
                    chapter = "",
                    author = "",
                    authorUrl = "",
                    publishedDate = "",
                    link = ""
                )

                try {
                    JsonReader(response.reader()).use { reader ->
                        reader.beginObject()
                        while (reader.hasNext()) {
                            try {
                                reader.skipValue()
                                val typeFav = reader.nextString()
                                reader.skipValue()
                                val specification = reader.nextString()
                                reader.skipValue()
                                val logo = reader.nextString()
                                reader.skipValue()
                                val logoBg = reader.nextString()
                                reader.skipValue()
                                val publisher = reader.nextString()
                                reader.skipValue()
                                val title = reader.nextString()
                                reader.skipValue()
                                val description = reader.nextString()
                                reader.skipValue()
                                val chapter = reader.nextString()
                                reader.skipValue()
                                val author = reader.nextString()
                                reader.skipValue()
                                val authorUrl = reader.nextString()
                                reader.skipValue()
                                val published = reader.nextString()
                                reader.skipValue()
                                val link = reader.nextString()

                                item = FavouriteItem(
                                    subject = subject,
                                    type = typeFav,
                                    id = itemToFind,
                                    publisherLogo = logo,
                                    publisherLogoBg = logoBg,
                                    publisherName = publisher,
                                    name = title,
                                    description = description,
                                    specification = specification,
                                    chapter = chapter,
                                    author = author,
                                    authorUrl = authorUrl,
                                    publishedDate = published,
                                    link = link
                                )
                                println("Assigned")
                            } catch (e: Exception) {
                                println(reader.peek())
                            }
                            reader.endObject()
                        }
                    }
                } catch (e: Exception) {
                    println(e.message)
                }

                item
            } else {
                println("Negative response")

                FavouriteItem(
                    subject = subject,
                    type = type,
                    id = itemToFind,
                    publisherLogo = "",
                    publisherLogoBg = "",
                    publisherName = "",
                    name = "",
                    description = "",
                    specification = "",
                    chapter = "",
                    author = "",
                    authorUrl = "",
                    publishedDate = "",
                    link = ""
                )
            }
        }

        suspend fun refreshFavourites() {
            val userId = LoginStatus.getUserID()
            val responseAccount =
                RequestURL("https://accorm.ginastic.co/300/login/UserData/?access-id=4954kvti4&unique-id=$userId")
                    ?: return

            try {
                println(responseAccount)
                val json = Json { ignoreUnknownKeys = true }
                val loginData = json.decodeFromString<LoginData>(responseAccount)

                LoginStatus.updateFavourites(loginData.addons.favs)
            } catch (e: Exception) {
                println(e.message)
            }

            if (refreshFavs > 10) refreshFavs -= 1 else refreshFavs += 1
        }

        suspend fun parseFavourites(): List<String> {
            val favourites = LoginStatus.getFavourites().split(" ")
            println(favourites)

            val modifiedFavourites = mutableListOf<String>()

            favourites.forEach {
                val fav = it.split("-")

                val subject = when (fav[0]) {
                    "ac" -> "accounting"
                    "bi" -> "biology"
                    "ch" -> "chemistry"
                    "ph" -> "physics"
                    "cs" -> "cs"
                    "fl" -> "fle"
                    "es" -> "esl"
                    "hi" -> "history"
                    else -> "ge"
                }

                val data = RequestURL("accorm.ginastic.co/300/login/RsrcsData/?access-id=230gri5q4&unique-id=${fav[0]}&subject=$subject") ?: ""

                if (data != "") {
                    val json = Json { ignoreUnknownKeys = true }
                    val favData = json.decodeFromString<SingleFavData>(data)

                    modifiedFavourites.add("${favData.specification}-$subject-${favData.type}-${fav[0]}")
                }
            }

            return modifiedFavourites
        }

        LaunchedEffect(Unit) {
            refreshFavourites()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            if (!LoginStatus.getLoginStatus()) {
                LoginStatus.clearSavedLoginData()
                val navigator = LocalNavigator.currentOrThrow
                navigator.push(Login)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val bg = parseColor(LoginStatus.getLogoBg())

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth(if (device == "Android" && !landscapeTablet) 0.9f else 0.5f)
                            .border(
                                width = 2.dp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(118, 78, 255),
                                        Color(157, 78, 255)
                                    )
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF35354a))
                            .padding(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(96.dp)
                                .height(96.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(bg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = LoginStatus.getLogo(),
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = LoginStatus.getName(),
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = LoginStatus.getEmail(),
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            val navigator = LocalNavigator.currentOrThrow
                            Image(
                                imageVector = FontAwesomeIcons.Solid.SignOutAlt,
                                contentDescription = "Logout",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(5.dp)
                                    .clickable {
                                        LoginStatus.clearSavedLoginData()
                                        navigator.push(Login)
                                    },
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
                Text(
                    text = "My Downloads",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = if (device == "Android" && !landscapeTablet) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "No Downloads",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "My Favourites",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = if (device == "Android" && !landscapeTablet) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                var favourites by remember { mutableStateOf(listOf<String>()) }

                @Composable
                fun FavouriteDisplayItem(item: FavouriteItem) {
                    println(item)
                    if (item.name != "") {
                        if (item.type == "note") {
                            DisplayNotesItem(
                                subjectRetrieve = item.subject,
                                uniqueId = item.id.toInt(),
                                logo = item.publisherLogo,
                                logoColor = parseColor(item.publisherLogoBg),
                                publisher = item.publisherName,
                                title = item.name,
                                description = item.description,
                                specification = item.specification,
                                chapter = item.chapter,
                                published = item.publishedDate,
                                url = item.link,
                                credit = item.author,
                                creditUrl = item.authorUrl,
                                backgroundColor = Color(0xFFfa8616)
                            )
                        } else {
                            DisplayVideosItem(
                                subjectRetrieve = item.subject,
                                uniqueId = item.id.toInt(),
                                logo = item.publisherLogo,
                                logoColor = parseColor(item.publisherLogoBg),
                                publisher = item.publisherName,
                                title = item.name,
                                description = item.description,
                                specification = item.specification,
                                chapter = item.chapter,
                                published = item.publishedDate,
                                url = item.link,
                                source = item.link.substringAfter("://").substringBefore("/"),
                                backgroundColor = Color(0xFFfa8616)
                            )
                        }
                    }
                }

                var connected: String? by remember { mutableStateOf("") }

                if (LoginStatus.getFavourites() != "" && refreshFavs >= 0) {

                    val coroutineScope = rememberCoroutineScope()
                    coroutineScope.launch {
                        connected = RequestURL("https://accorm.ginastic.co/300/true/")
                    }

                    if (connected == "true") {
                        var canLoad by remember { mutableStateOf(false) }

                        coroutineScope.launch {
                            favourites = parseFavourites()
                            println(favourites)
                            canLoad = true
                        }

                        if (canLoad && favourites.isNotEmpty()) {
                            favourites.forEach {
                                var item by remember {
                                    mutableStateOf(
                                        FavouriteItem(
                                            subject = "",
                                            type = "",
                                            id = "",
                                            publisherLogo = "",
                                            publisherLogoBg = "",
                                            publisherName = "",
                                            name = "",
                                            description = "",
                                            specification = "",
                                            chapter = "",
                                            author = "",
                                            authorUrl = "",
                                            publishedDate = "",
                                            link = ""
                                        )
                                    )
                                }

                                var canDisplay by remember { mutableStateOf(false) }

                                println(it)

                                val parsedData = it.split("-")

                                println(parsedData)

                                coroutineScope.launch {
                                    item = retrieveFromUrl(
                                        subject = parsedData[1],
                                        url = "https://accorm.ginastic.co/300/login/RsrcsData/?access-id=230gri5q4&unique-id=${parsedData[3]}&subject=${parsedData[1]}",
                                        itemToFind = parsedData[3],
                                        type = parsedData[2]
                                    )
                                    canDisplay = true
                                }

                                if (canDisplay) {
                                    FavouriteDisplayItem(item)
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        } else if (favourites.isEmpty()) {
                            Text(
                                text = "Something went wrong",
                                color = Color.Red,
                                fontFamily = poppins,
                                fontSize = 24.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        } else {
                            CircularProgressIndicator(color = Color.White)
                        }
                    } else if (connected == "") {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = "Favourites requires an internet connection",
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 24.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        text = "No favourites",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                CopyrightMessage()
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

data class FavouriteItem(
    val subject: String,
    val type: String,
    val id: String,
    val publisherLogo: String,
    val publisherLogoBg: String,
    val publisherName: String,
    val name: String,
    val description: String,
    val specification: String,
    val chapter: String,
    val author: String,
    val authorUrl: String,
    val publishedDate: String,
    val link: String
)

@Serializable
data class SingleFavData(
    @SerialName("type") val type: String,
    @SerialName("specification") val specification: String,
    @SerialName("pub_logo") val logo: String,
    @SerialName("pub_logo_bg") val logoBg: String,
    @SerialName("pub_name") val publisher: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("chapter") val chapter: String,
    @SerialName("author") val author: String,
    @SerialName("author_url") val authorUrl: String,
    @SerialName("published_date") val published: String,
    @SerialName("link") val link: String
)