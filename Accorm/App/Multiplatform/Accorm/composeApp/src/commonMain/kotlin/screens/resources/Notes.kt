package screens.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
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
            Text(
                text = "Notes",
                modifier = Modifier
                    .clickable {
                        CurrentSubject.setUrl("https://accorm.ginastic.co/700/IGCSE/accounting/Copy%20notes%20%28Part%202%29_506650041.pdf")
                        navigator.push(DisplayResource)
                    }
            )
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


        }
    }
}