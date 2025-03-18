package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import compose.icons.fontawesomeicons.solid.Download
import database.AccormDatabase
import database.DownloadsDataSource
import screens.poppins
import screens.resources.DisplayNotesItem

class Downloads : Tab {
    private fun readResolve(): Any = Downloads()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Download)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Downloads",
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
                    .background(Color(31, 31, 54))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "My Downloads",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
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
                        isDownload = true,
                        level = item.specification
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
            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}

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