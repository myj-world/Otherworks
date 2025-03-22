package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.Search
import kotlinx.coroutines.launch
import network.RequestURL
import screens.assets.DictionaryResponse
import screens.assets.parseDictionaryJson
import screens.poppins

class Dictionary : Tab {
    private fun readResolve(): Any = Dictionary()
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Dictionary",
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var word by remember { mutableStateOf("") }

                var response: String? by remember { mutableStateOf("") }
                val coroutineScope = rememberCoroutineScope()

                var result by remember { mutableStateOf(listOf<DictionaryResponse>()) }
                var loading by remember { mutableStateOf(false) }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = word,
                        onValueChange = { word = it },
                        label = { Text("Enter a word") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(5f),
                        enabled = !loading,
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                loading = true
                                response = RequestURL("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
                                result = if (response != null) {
                                    try {
                                        parseDictionaryJson(response!!)
                                    } catch (e: Exception) {
                                        listOf()
                                    }
                                } else {
                                    listOf()
                                }
                                loading = false
                            }
                        },
                        enabled = !loading,
                        modifier = Modifier
                            .weight(1f)
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
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                LazyColumn {
                    items(result.size) { itemIndex ->
                        var audioUrl by remember { mutableStateOf("") }

                        Text(
                            result[itemIndex].word,
                            color = Color(0xFFacacf9),
                            fontFamily = poppins,
                            fontSize = 40.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        for (i in result[itemIndex].meanings) {
                            Text(
                                i.partOfSpeech,
                                color = Color(0xffDEDEFB),
                                fontFamily = poppins,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            for (j in i.definitions) {
                                Text(
                                    j.definition,
                                    color = Color.White,
                                    fontFamily = poppins,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                        }
                        for (i in result[itemIndex].phonetics ?: emptyList()) {
                            if (i.audio != "") audioUrl = i.audio!!
                        }
                        if (audioUrl != "") {
                            println(audioUrl)

                        } else {
                            println("Audio not found")
                        }
                        Divider(
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}