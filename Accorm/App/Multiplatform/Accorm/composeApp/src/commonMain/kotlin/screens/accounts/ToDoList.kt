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
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import compose.icons.fontawesomeicons.solid.List
import database.AccormDatabase
import database.ToDoListDataSource
import screens.poppins

object ToDoList : Tab {
    private fun readResolve(): Any = ToDoList
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.List)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "To Do List",
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
                    text = "Todo List",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                var item by remember { mutableStateOf("Dummy item") }
                TextField(
                    value = item,
                    onValueChange = { item = it },
                    label = { Text("Enter item to add") },
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Items",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                val db = AccormDatabase.database
                val toDoListDataSource = ToDoListDataSource(db = db)
                toDoListDataSource.addToList(item = item, complete = false)
                val list = toDoListDataSource.getList()

                list.forEach {
                    println(it.item)
                }

            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}