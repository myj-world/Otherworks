package screens.accounts

import accounts.LoginStatus
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.List
import compose.icons.fontawesomeicons.solid.PlusCircle
import compose.icons.fontawesomeicons.solid.TimesCircle
import compose.icons.fontawesomeicons.solid.Trash
import database.AccormDatabase
import database.ToDoListDataSource
import screens.poppins

class ToDoList : Tab {
    private fun readResolve(): Any = ToDoList()
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
                val db = AccormDatabase.database
                val toDoListDataSource = ToDoListDataSource(db = db)
                val list = toDoListDataSource.getList()
                val navigator = LocalNavigator.currentOrThrow

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

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    var item by remember { mutableStateOf("") }
                    TextField(
                        value = item,
                        onValueChange = { item = it },
                        label = { Text("Enter item to add") },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            placeholderColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Button(
                        onClick = {
                            if (item.isNotEmpty()) {
                                toDoListDataSource.addToList(item = item, complete = false)
                                item = ""
                                navigator.pop()
                                navigator.push(ToDoList())
                            }
                        }
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.PlusCircle,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(35.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.width(1.dp))
                    Button(
                        onClick = {
                            toDoListDataSource.clearList()
                            navigator.pop()
                            navigator.push(ToDoList())
                        }
                    ) {
                        Image(
                            imageVector = FontAwesomeIcons.Solid.Trash,
                            contentDescription = "Empty",
                            modifier = Modifier
                                .size(35.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }

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

                list.forEach {
                    println(it)
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF2f2f41))
                        ) {
                            val icon = if (it.complete?.toInt() == 1) {
                                FontAwesomeIcons.Solid.CheckCircle
                            } else {
                                FontAwesomeIcons.Solid.TimesCircle
                            }

                            Image(
                                imageVector = icon,
                                contentDescription = "Completion",
                                modifier = Modifier
                                    .padding(20.dp)
                                    .size(30.dp)
                                    .clickable {
                                        if (it.complete == 0L) {
                                            toDoListDataSource.completeItem(it.itemId)
                                        } else {
                                            toDoListDataSource.inCompleteItem(it.itemId)
                                        }
                                        navigator.pop()
                                        navigator.push(ToDoList())
                                    },
                                colorFilter = ColorFilter.tint(if (it.complete != 0L) Color.Green else Color.Red)
                            )

                            Text(
                                text = it.item,
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            imageVector = FontAwesomeIcons.Solid.Trash,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    toDoListDataSource.deleteItem(it.itemId)
                                    navigator.pop()
                                    navigator.push(ToDoList())
                                },
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (list.isEmpty()) {
                    Text(
                        text = "No items to show",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                }
            }
        } else {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }
    }
}