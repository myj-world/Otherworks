package screens.resources

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
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
import compose.icons.fontawesomeicons.solid.File
import kotlinx.coroutines.launch
import network.RequestURL
import screens.assets.CopyrightMessage
import screens.device
import screens.landscapeTablet
import screens.poppins
import viewmodels.CurrentSubject

object Syllabus : Tab {
    private fun readResolve(): Any = Syllabus
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Syllabus",
                    icon = icon
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
                "Islamiyat" -> "0493"
                "History" -> "04481"
                "Geography" -> "04482"
                "Accounting" -> "0452"
                "Physics" -> "0625"
                "Chemistry" -> "0620"
                "Biology" -> "0610"
                "CS" -> "0478"
                "FLE" -> "0500"
                "ESL" -> "0510"
                else -> "0580"
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
            navigator.pop()
        }
        var result by remember {
            mutableStateOf("")
        }
        var syllabusLink by remember {
            mutableStateOf("")
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            result = RequestURL("https://accorm.ginastic.co/400/$subjectRetrieve.json").toString()
        }

        val resultArray = mutableMapOf<Int, String>()
        var decode by remember { mutableStateOf(false) }

        if (result != "") {
            try {
                JsonReader(result.reader()).use { reader ->
                    reader.beginObject()
                    val token = reader.peek()
                    if (token.equals(JsonToken.NAME)) {
                        try {
                            reader.nextName()
                            reader.beginObject()
                            try {
                                while (reader.hasNext() && (reader.peek() == JsonToken.NAME || reader.peek() == JsonToken.STRING)) {
                                    resultArray[reader.nextName().toInt()] = reader.nextString()
                                    println(resultArray)
                                }
                                while (true) {
                                    try {
                                        if (reader.peek() == JsonToken.NAME) {
                                            if (reader.nextName() == "file") {
                                                reader.beginObject()
                                                println("Tests " + reader.nextName())
                                                syllabusLink = reader.nextString()
                                                println(resultArray)
                                                break
                                            } else {
                                                reader.skipValue()
                                            }
                                        } else {
                                            println(reader.peek())
                                            reader.skipValue()
                                        }
                                    } catch (e: Exception) {
                                        println(e)
                                        println(reader.peek())
                                        break
                                    }
                                }
                            } catch (e: Exception) {
                                println(reader.peek())
                            }
                            reader.endObject()
                        } catch (e: Exception) {
                            reader.skipValue()
                        }
                    }
                }
            } catch (_: Exception) {}
            decode = true
            println("Tests $syllabusLink")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
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
                Column (
                    modifier = Modifier
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    @Composable
                    fun Chapter(
                        number: Int,
                        title: String
                    ) {
                        Row {
                            Text(
                                text = "$number",
                                color = Color.Gray,
                                fontFamily = poppins,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = title,
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 20.sp,
                                modifier = Modifier.width(290.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    if (result != "") {
                        if (decode) {
                            resultArray.forEach { (number, title) ->
                                Chapter(number, title)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                CurrentSubject.setUrl(syllabusLink)
                                navigator.push(DisplayResourceExternal())
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(320.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(172, 172, 249)
                            )
                        ) {
                            Image(
                                imageVector = FontAwesomeIcons.Solid.File,
                                contentDescription = "Syllabus",
                                modifier = Modifier
                                    .size(20.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = "Full Syllabus Outline",
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 20.sp
                            )
                        }
                    } else {
                        Row {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = "Loading...",
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    CopyrightMessage()
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    }
}