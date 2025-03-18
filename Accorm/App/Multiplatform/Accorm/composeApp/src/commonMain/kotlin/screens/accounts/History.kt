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
import compose.icons.fontawesomeicons.solid.History
import database.AccormDatabase
import database.HistoryDataSource
import screens.poppins
import screens.resources.DisplayNotesItem
import screens.resources.DisplayVideosItem
import screens.resources.parseColor

class History : Tab {
    private fun readResolve(): Any = History()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.History)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "History",
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
                val historyDataSource = HistoryDataSource(AccormDatabase.database)
                val history = historyDataSource.getHistory()

                Text(
                    text = "History",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))

                if (history.isNotEmpty()) {
                    history.forEach {
                        println(it)
                        if (it.contentType == "NOTE") {
                            DisplayNotesItem(
                                subjectRetrieve = it.subject,
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
                                downloadIconColor = parseColor(it.downloadIconColor.toString()),
                                level = it.specification
                            )
                        } else {
                            DisplayVideosItem(
                                subjectRetrieve = it.subject,
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
                                logoTextColour = parseColor(it.logoTextColour.toString()),
                                level = it.specification
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
            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}