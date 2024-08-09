package screens.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowRight
import screens.device
import screens.landscapeTablet
import screens.poppins
import screens.resources.Notes
import screens.resources.PPQs
import screens.resources.Syllabus
import screens.resources.Videos
import viewmodels.CurrentSubject

@Composable
fun Subject(
    title: String,
    code: String,
    notes: Boolean = true,
    videos: Boolean = true,
    ppqs: Boolean = true,
    syllabus: Boolean = true,
    grade: String
) {
    val nav = LocalNavigator.currentOrThrow

    fun notesClick() {
        CurrentSubject.setSubject(title)
        CurrentSubject.setLevel(grade)
        nav.push(Notes)
    }
    fun videosClick() {
        CurrentSubject.setSubject(title)
        nav.push(Videos)
    }
    fun ppqsClick() {
        CurrentSubject.setSubject(title)
        nav.push(PPQs)
    }
    fun syllabusClick() {
        CurrentSubject.setSubject(title)
        nav.push(Syllabus)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(10.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(153, 109, 194),
                        Color(106, 106, 193)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(15.dp)
    ) {
        Text(
            text = code,
            color = Color.White,
            fontFamily = poppins,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = title,
            color = Color.White,
            fontFamily = poppins,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (device == "Android" && !landscapeTablet) {
            Row {
                if (notes) {
                    Button(
                        onClick = { notesClick() },
                        modifier = Modifier
                            .fillMaxWidth(if (videos) 0.5f else 1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Notes",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Notes",
                            tint = Color.White
                        )
                    }
                }
                if (videos) {
                    Button(
                        onClick = { videosClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Videos",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Videos",
                            tint = Color.White
                        )
                    }
                }
            }
            Row {
                if (ppqs) {
                    Button(
                        onClick = { ppqsClick() },
                        modifier = Modifier
                            .fillMaxWidth(if (syllabus) 0.5f else 1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "PPQs",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "PPQs",
                            tint = Color.White
                        )
                    }
                }
                if (syllabus) {
                    Button(
                        onClick = { syllabusClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Syllabus",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Syllabus",
                            tint = Color.White
                        )
                    }
                }
            }
        } else {
            Row {
                if (notes) {
                    Button(
                        onClick = { notesClick() },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Notes",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Notes",
                            tint = Color.White
                        )
                    }
                }
                if (videos) {
                    Button(
                        onClick = { videosClick() },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Videos",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Videos",
                            tint = Color.White
                        )
                    }
                }
                if (ppqs) {
                    Button(
                        onClick = { ppqsClick() },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "PPQs",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "PPQs",
                            tint = Color.White
                        )
                    }
                }
                if (syllabus) {
                    Button(
                        onClick = { syllabusClick() },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(169, 150, 246)
                        )
                    ) {
                        Text(
                            text = "Syllabus",
                            color = Color.White,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(15.dp),
                            contentDescription = "Syllabus",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}