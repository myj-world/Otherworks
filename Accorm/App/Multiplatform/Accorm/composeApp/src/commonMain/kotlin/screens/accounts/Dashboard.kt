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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SignOutAlt
import compose.icons.fontawesomeicons.solid.User
import database.AccormDatabase
import database.DownloadsDataSource
import database.HistoryDataSource
import database.ToDoListDataSource
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
//        var response by remember { mutableStateOf("") }
        var refreshFavs by remember { mutableIntStateOf(0) }
        var connected: String? by remember { mutableStateOf("") }
        val favs by remember { mutableStateOf(mutableListOf<SingleFavData>()) }
        var favsAdditionalData by remember { mutableStateOf(mutableListOf<FavsInfo>()) }
        var canLoad by remember { mutableStateOf(false) }
        var show by remember { mutableStateOf(false) }

        suspend fun refreshFavourites() {
            val userId = LoginStatus.getUserID()
            val responseAccount =
                RequestURL("https://accorm.ginastic.co/300/login/UserData/?access-id=4954kvti4&unique-id=$userId")
                    ?: return

            try {
                println(responseAccount)
                val json = Json { ignoreUnknownKeys = true }
                val loginData = json.decodeFromString<LoginData>(responseAccount)
                println(loginData)
                LoginStatus.updateName(loginData.accountData.name)
                LoginStatus.updateLogoBg(loginData.accountData.colour)
                LoginStatus.updateLogo(loginData.accountData.name.substring(0, 1))
                LoginStatus.updateFavourites(loginData.addons.favs)
            } catch (e: Exception) {
                println(e.message)
            }

            if (refreshFavs > 10) refreshFavs -= 1 else refreshFavs += 1
        }

        suspend fun parseFavourites() {
            val favourites = LoginStatus.getFavourites().trim().split(" ")
            println(favourites)

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
                    "ma" -> "maths"
                    else -> "geography"
                }

                val data =
                    RequestURL("https://accorm.ginastic.co/300/login/RsrcsData/?access-id=230gri5q4&unique-id=${fav[1]}&subject=$subject")
                        ?: ""
                println("https://accorm.ginastic.co/300/login/RsrcsData/?access-id=230gri5q4&unique-id=${fav[1]}&subject=$subject")
                println(data)

                if (data != "") {
                    val json = Json { ignoreUnknownKeys = true }
                    val favData = json.decodeFromString<SingleFavData>(data)

                    favs.add(favData)
                    favsAdditionalData.add(
                        FavsInfo(
                            subject = subject,
                            id = fav[1]
                        )
                    )
                }
            }
        }

        LaunchedEffect(Unit) {

            try {
                connected = RequestURL("https://accorm.ginastic.co/300/true/")

                if (connected == "true") {
                    refreshFavourites()
                    parseFavourites()
                    canLoad = true
                }
            } catch (e: Exception) {
                println(e.message)
                show = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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

                Spacer(modifier = Modifier.height(10.dp))
                val navigator = LocalNavigator.currentOrThrow
                Text(
                    text = "My  Todo List",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = if (device == "Android" && !landscapeTablet) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.clickable {
                        navigator.push(ToDoList)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))

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

                @Composable
                fun DownloadDisplayItem(item: DownloadItem) {
                    println(item)
                    DisplayNotesItem(
                        subjectRetrieve = item.subject,
                        uniqueId = item.id.toInt(),
                        logo = item.publisherLogo,
                        logoBg = "#acacf9",
                        publisher = item.publisherName,
                        title = item.name,
                        description = item.description,
                        specification = item.specification,
                        chapter = item.chapter,
                        published = item.publishedDate,
                        credit = item.author,
                        creditUrl = item.authorUrl,
                        backgroundColor = Color(0xFF56567d),
                        textColor = Color.White,
                        labelColor = Color.Gray,
                        logoTextColour = Color.White,
                        isDownload = true
                    )
                }


                val downloadsDataSource = DownloadsDataSource(AccormDatabase.database)
                val downloads = downloadsDataSource.retrieveAllDownloads()

                if (downloads.isNotEmpty()) {
                    downloads.forEach {
                        val item = DownloadItem(
                            subject = it.subject,
                            type = it.type,
                            id = it.uniqueid.toString(),
                            publisherLogo = it.publisherlogo,
                            publisherLogoBg = it.publisherlogobg,
                            publisherName = it.publisher,
                            name = it.title,
                            description = it.description,
                            specification = it.specification,
                            chapter = it.chapter,
                            author = it.author,
                            authorUrl = it.authorcrediturl,
                            publishedDate = it.publisheddate,
                            file1nameondisk = it.file1nameondisk,
                            file2nameondisk = it.file2nameondisk
                        )
                        DownloadDisplayItem(
                            item = item
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    Text(
                        text = "No Downloads",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

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

                @Composable
                fun FavouriteDisplayItem(item: FavouriteItem) {
                    println(item)
                    if (item.name != "") {
                        if (item.type == "note") {
                            DisplayNotesItem(
                                subjectRetrieve = item.subject,
                                uniqueId = item.id.toInt(),
                                logo = item.publisherLogo,
                                logoBg = "#ffce4d",
                                publisher = item.publisherName,
                                title = item.name,
                                description = item.description,
                                specification = item.specification,
                                chapter = item.chapter,
                                published = item.publishedDate,
                                url = item.link,
                                credit = item.author,
                                creditUrl = item.authorUrl,
                                backgroundColor = Color(0xFFffb900),
                                textColor = Color.Black,
                                labelColor = Color(0xFF373120),
                                logoTextColour = Color.Black,
                                downloadIconColor = Color.Black
                            )
                        } else {
                            DisplayVideosItem(
                                subjectRetrieve = item.subject,
                                uniqueId = item.id.toInt(),
                                logo = item.publisherLogo,
                                logoColor = parseColor("#ffce4d"),
                                publisher = item.publisherName,
                                title = item.name,
                                description = item.description,
                                specification = item.specification,
                                chapter = item.chapter,
                                published = item.publishedDate,
                                url = item.link,
                                source = item.link.substringAfter("://").substringBefore("/"),
                                backgroundColor = Color(0xFFffb900),
                                textColor = Color.Black,
                                labelColor = Color(0xFF373120),
                                logoTextColour = Color.Black
                            )
                        }
                    }
                }

                if (canLoad && favs.isNotEmpty() && !show) {

                    var i = 0

                    favs.forEach {
                        val item = FavouriteItem(
                            subject = favsAdditionalData[i].subject,
                            type = it.type,
                            id = favsAdditionalData[i].id,
                            publisherLogo = it.logo,
                            publisherLogoBg = it.logoBg,
                            publisherName = it.publisher,
                            name = it.title,
                            description = it.description,
                            specification = it.specification,
                            chapter = it.chapter,
                            author = it.author,
                            authorUrl = it.authorUrl,
                            publishedDate = it.published,
                            link = it.link
                        )

                        FavouriteDisplayItem(
                            item = item
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        i++
                    }
                } else if (LoginStatus.getFavourites().trim().split(" ").isEmpty() || show) {
                    Text(
                        text = "No favourites",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "History",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = if (device == "Android" && !landscapeTablet) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                val historyDataSource = HistoryDataSource(AccormDatabase.database)
                val history = historyDataSource.getHistory()
                if (history.isNotEmpty()) {
                    history.forEach {
                        println(it)
                        if (it.contentType == "NOTE") {
                            DisplayNotesItem(
                                subjectRetrieve = it.subjectRetrieve,
                                uniqueId = it.uniqueId.toInt(),
                                logo = it.logo,
                                logoBg = it.logoBg.toString(),
                                publisher = it.publisher,
                                title = it.title,
                                description = it.description,
                                specification = it.specification,
                                chapter = it.chapter,
                                published = it.published,
                                url = it.url.toString(),
                                credit = it.credit.toString(),
                                creditUrl = it.creditUrl.toString(),
                                backgroundColor = parseColor(it.backgroundColor.toString()),
                                textColor = parseColor(it.textColor.toString()),
                                labelColor = parseColor(it.labelColor.toString()),
                                logoTextColour = parseColor(it.logoTextColour.toString()),
                                downloadIconColor = parseColor(it.downloadIconColor.toString())
                            )
                        } else {
                            DisplayVideosItem(
                                subjectRetrieve = it.subjectRetrieve,
                                uniqueId = it.uniqueId.toInt(),
                                logo = it.logo,
                                logoColor = parseColor(it.logoBg.toString()),
                                publisher = it.publisher,
                                title = it.title,
                                description = it.description,
                                specification = it.specification,
                                chapter = it.chapter,
                                published = it.published,
                                url = it.url.toString(),
                                source = it.source.toString(),
                                backgroundColor = parseColor(it.backgroundColor.toString()),
                                textColor = parseColor(it.textColor.toString()),
                                labelColor = parseColor(it.labelColor.toString()),
                                logoTextColour = parseColor(it.logoTextColour.toString())
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    Text(
                        text = "No notes or videos opened yet.",
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

data class FavsInfo(
    val subject: String,
    val id: String
)

data class DownloadItem(
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
    val file1nameondisk: String,
    val file2nameondisk: String
)