package screens.resources

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import compose.icons.fontawesomeicons.solid.ExternalLinkAlt
import compose.icons.fontawesomeicons.solid.Link
import kotlinx.coroutines.launch
import network.RequestURL
import screens.assets.CopyrightMessage
import screens.lexend
import screens.poppins
import viewmodels.CurrentSubject

object Notes : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Notes",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
                    "Computer Science" -> "computer_science"
                    "FLE" -> "fle"
                    "ESL" -> "esl"
                    else -> "maths"
                }
                subjectCode = when (CurrentSubject.getSubject()) {
                    "Islamiyat" -> "0493"
                    "Pakistan Studies, \n \n History" -> "0448"
                    "Pakistan Studies, \n \n Geography" -> "0448"
                    "Accounting" -> "0452"
                    "Physics" -> "0625"
                    "Chemistry" -> "0620"
                    "Biology" -> "0610"
                    "Computer Science" -> "0478"
                    "FLE" -> "0500"
                    "ESL" -> "0510"
                    else -> "0580"
                }
            } else {
                navigator.pop()
            }
            val coroutineScope = rememberCoroutineScope()
            var data by remember { mutableStateOf("") }
            coroutineScope.launch {
                data =
                    RequestURL("https://accorm.ginastic.co/300/fetch/?access-id=65aea3e3e6184&subject=$subjectRetrieve")
                        ?: "{\"1\": {\"unique_id\": 000000000,\"title\": \"Sample Notes\",\"url\": \"https://example.com\",\"chapter\": \"miscellaneous\",\"publisher\": \"Accorm\",\"pub_type\": \"Admin\",\"logo\": \"A\",\"logo_bg\": \"#000000\", \"specification:\": \"Sample Notes\", \"author\": \"Accorm\", \"published\": \"12/12/2023\", \"description\": \"Sample Notes\"}, \"num-of-rows\": 1}"
            }
            @Composable
            fun Item(
                logo: String,
                logoColor: Color,
                chapter: String,
                publisher: String,
                title: String,
                description: String,
                specification: String,
                author: String,
                published: String,
                url: String
            ) {
                var isExpanded by remember {
                    mutableStateOf(false)
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(28, 28, 28))
                        .padding(10.dp)
                        .clickable { isExpanded = !isExpanded },
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .background(logoColor)
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = logo,
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = publisher,
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        text = title,
                        fontFamily = poppins,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = description,
                            fontSize = 12.sp,
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
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = specification,
                            fontSize = 12.sp,
                            fontFamily = poppins,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Chapter",
                            fontSize = 12.sp,
                            fontFamily = lexend,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = chapter,
                            fontSize = 12.sp,
                            fontFamily = poppins,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Author",
                            fontSize = 12.sp,
                            fontFamily = lexend,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = author,
                            fontSize = 12.sp,
                            fontFamily = poppins,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Published on",
                            fontSize = 12.sp,
                            fontFamily = lexend,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = published,
                            fontSize = 12.sp,
                            fontFamily = poppins,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
//                            Button(
//                                onClick = {
//
//                                },
//                                modifier = Modifier
//                                    .fillMaxWidth(0.2f),
//                                colors = ButtonDefaults.buttonColors(
//                                    backgroundColor = Color.White
//                                )
//                            ) {
//                                Image(
//                                    imageVector = FontAwesomeIcons.Solid.Link,
//                                    contentDescription = "Copy Link",
//                                    colorFilter = ColorFilter.tint(Color(172, 172, 249)),
//                                    modifier = Modifier
//                                        .size(15.dp)
//                                )
//                            }

                            Spacer(modifier = Modifier.width(5.dp))

                            Button(
                                onClick = {
                                    CurrentSubject.setUrl(url)
                                    navigator.push(DisplayResource)
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(172, 172, 249)
                                )
                            ) {
                                Image(
                                    imageVector = FontAwesomeIcons.Solid.ExternalLinkAlt,
                                    contentDescription = "Open",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .size(15.dp)
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
            Item(
                logo = "M",
                logoColor = Color.Black,
                publisher = "M. Yousuf Jamil",
                title = "Sample Notes",
                description = "Sample Notes",
                specification = "Sample Notes",
                author = "Accorm",
                chapter = "miscellaneous",
                published = "12/12/2023",
                url = "https://accorm.ginastic.co/700/IGCSE/islamiyat/The%20Life%20Of%20The%20Prophet%20%28PBUH%29_889590849.pdf"
            )

            Spacer(modifier = Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}