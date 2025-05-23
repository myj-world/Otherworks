package screens.resources

import analytics.LogEvent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import screens.assets.CopyrightMessage
import screens.device
import screens.landscapeTablet
import screens.poppins
import viewmodels.ColourProvider
import viewmodels.CurrentSubject

object PPQs : Tab {
    private fun readResolve(): Any = PPQs
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Book)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "PPQs",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("Load PPQs ${CurrentSubject.getSubject()}", null, null)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val navigator = LocalNavigator.currentOrThrow
            var subjectCode by remember {
                mutableStateOf("")
            }
            var level by remember {
                mutableStateOf("")
            }
            var levelRetrieve by remember {
                mutableStateOf("")
            }
            var subjectRetrieve by remember {
                mutableStateOf("")
            }
            var codeRetrieve by remember {
                mutableStateOf("")
            }
            level = CurrentSubject.getLevel()

            if (level == "IGCSE / O Level") {
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
                codeRetrieve = when (CurrentSubject.getSubject()) {
                    "Islamiyat" -> "0493"
                    "History" -> "0448"
                    "Geography" -> "0448"
                    "Accounting" -> "0452"
                    "Physics" -> "0625"
                    "Chemistry" -> "0620"
                    "Biology" -> "0610"
                    "CS" -> "0478"
                    "FLE" -> "0500"
                    "ESL" -> "0510"
                    else -> "0580"
                }
                subjectRetrieve = when (CurrentSubject.getSubject()) {
                    "CS" -> "Computer-Science-$codeRetrieve"
                    "Maths" -> "Mathematics-$codeRetrieve"
                    "FLE" -> "English-First-Language-$codeRetrieve"
                    "ESL" -> "English-Second-Language-oral-endorsement-$codeRetrieve"
                    "Islamiyat" -> "Islamiyat%20-%20$codeRetrieve"
                    "History" -> "Pakistan-Studies-$codeRetrieve"
                    "Geography" -> "Pakistan-Studies-$codeRetrieve"
                    else -> "${CurrentSubject.getSubject()}-$codeRetrieve"
                }
                levelRetrieve = "IGCSE"

                println("Tests $subjectRetrieve $subjectCode")
            } else {
                subjectCode = when (CurrentSubject.getSubject()) {
                    "Physics" -> "9702"
                    "Chemistry" -> "9701"
                    "Biology" -> "9700"
                    "CS" -> "9618"
                    else -> "9709"
                }
                codeRetrieve = subjectCode
                subjectRetrieve = when (CurrentSubject.getSubject()) {
                    "Maths" -> "Mathematics-$codeRetrieve"
                    "CS" -> "Computer%20Science%20(for%20first%20examination%20in%202021)%20($codeRetrieve)"
                    else -> "${CurrentSubject.getSubject()}-$codeRetrieve"
                }
                levelRetrieve = "A-Level"

                println("Tests $subjectRetrieve $subjectCode")
            }

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

                val papers = KnowPapersBySubject(CurrentSubject.getSubject(), CurrentSubject.getLevel())
                val years = KnowYearsBySubject(CurrentSubject.getSubject())
                val sessions = KnowSessionsBySubject(CurrentSubject.getSubject())

                for (year in years.reversed()) {
                    for (session in sessions.reversed()) {

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "$year - $session",
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = if (device == "Android" && !landscapeTablet) 25.sp else 50.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        for (paper in papers) {
                            if (!((paper == "32" && session == "Oct/Nov" && (subjectCode == "9700" || subjectCode == "9701" || subjectCode == "9702")) || (subjectCode == "9618" && year == 2022) || (subjectCode == "0511" && year == 2024 && (paper == "31" || paper == "32" || paper == "33")))) {
                                val yearRetrieve = year.toString().substring(2, 4)
                                val sessionRetrieve =
                                    if (session == "May/June") "s" else if (session == "Oct/Nov") "w" else "m"

                                Paper(
                                    title = "$subjectCode ${CurrentSubject.getSubject()} - Paper $paper",
                                    session = session,
                                    year = year.toString(),
                                    qpLink = "https://pastpapers.papacambridge.com/directories/CAIE/CAIE-pastpapers/upload/" + codeRetrieve + "_" + sessionRetrieve + yearRetrieve + "_qp_$paper.pdf",
                                    msLink = "https://pastpapers.papacambridge.com/directories/CAIE/CAIE-pastpapers/upload/" + codeRetrieve + "_" + sessionRetrieve + yearRetrieve + "_ms_$paper.pdf",
                                )
                            }
                        }
                    }
                }

                if (years.isEmpty()) {
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

@Composable
fun Paper(
    title: String,
    session: String,
    year: String,
    qpLink: String,
    msLink: String
) {
    println("$title $qpLink $msLink")
    val navigator = LocalNavigator.currentOrThrow

    if (device != "Android" || landscapeTablet) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
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
                .fillMaxWidth(0.8f)
                .padding(20.dp)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$session - $year",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 20.sp
                )
            }
            Row {
                Button(
                    onClick = {
                        CurrentSubject.setUrl(msLink)
                        CurrentSubject.setUrlFileName(title)
                        navigator.push(DisplayResourcePDF())
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .height(55.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "MS",
                        color = parseColor(ColourProvider.colour1),
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        CurrentSubject.setUrl(qpLink)
                        CurrentSubject.setUrlFileName(title)
                        navigator.push(DisplayResourcePDF())
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = parseColor(ColourProvider.colour1)
                    ),
                    modifier = Modifier
                        .height(55.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "QP",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    } else {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
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
                .fillMaxWidth(0.8f)
                .padding(20.dp)
                .height(150.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$session - $year",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 18.sp
                )
            }
            Row {
                Button(
                    onClick = {
                        CurrentSubject.setUrl(msLink)
                        CurrentSubject.setUrlFileName(title)
                        navigator.push(DisplayResourcePDF())
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .height(55.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "MS",
                        color = parseColor(ColourProvider.colour1),
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        CurrentSubject.setUrl(qpLink)
                        CurrentSubject.setUrlFileName(title)
                        navigator.push(DisplayResourcePDF())
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = parseColor(ColourProvider.colour1)
                    ),
                    modifier = Modifier
                        .height(55.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "QP",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}