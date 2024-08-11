package screens.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screens.resources.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.MailBulk
import compose.icons.fontawesomeicons.solid.User
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import screens.Connected
import screens.device
import screens.landscapeTablet
import screens.poppins
import viewmodels.CurrentEmailName

@Composable
fun Person(
    name: String,
    icon: String,
    email: String,
    roles: List<Role>
) {
    val navigator = LocalNavigator.currentOrThrow
    fun itemClick() {
        navigator.push(Resources)
    }

    var connected by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        connected = Connected()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(10.dp)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(118, 78, 255),
                        Color(157, 78, 255)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color(25, 25, 44))
            .clickable { itemClick() }
            .padding(15.dp)
    ) {
        if (name != "Taqi Ahmed") {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.MailBulk,
                    contentDescription = "Contact",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            CurrentEmailName.setEmail(email)
                            CurrentEmailName.setName("")
                            navigator.push(Contact)
                        }
                )
            }
        }
        Row {
            if (connected) {
                KamelImage(
                    resource = asyncPainterResource(icon),
                    contentDescription = name,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(100)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.User,
                        contentDescription = name,
                        tint = Color(118, 78, 255),
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = name,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        val maxRowCount = when (name) {
            "M. Musab Khan" -> 3
            "Abdullah Kamil" -> 2
            else -> 1
        }
        var roleCount by remember { mutableIntStateOf(0) }
        Row {
            roleCount = 0
            roles.forEach { role ->
                if (roleCount < maxRowCount) {
                    var showInfo by remember { mutableStateOf(false) }
                    if (showInfo && (role.role.contains("Hoster") || role.role.contains("App Developer"))) {
                        AlertDialog(
                            title = {
                                Text(text = "About Role")
                            },
                            text = {
                                Text(text = role.clickPopup)
                            },
                            onDismissRequest = { showInfo = false },
                            confirmButton = {
                                TextButton(
                                    onClick = { showInfo = false }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    if (device == "Android" && !landscapeTablet) {
                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .height(35.dp)
                                .weight(1f)
                                .border(
                                    2.dp,
                                    if (!role.majorRole) Color(114, 116, 244) else Color(
                                        145,
                                        116,
                                        214
                                    ),
                                    RoundedCornerShape(50)
                                )
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (role.majorRole) Color(145, 116, 214) else Color(
                                        25,
                                        25,
                                        44
                                    )
                                )
                                .clickable {
                                    if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                                        showInfo = true
                                    }
                                }
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = role.role,
                                color = if (role.majorRole) Color.White else Color(145, 116, 244),
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .fillMaxHeight()
                            )
                            if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(100))
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.Info,
                                        contentDescription = "Info",
                                        tint = Color(145, 116, 244),
                                        modifier = Modifier
                                            .size(10.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .height(35.dp)
                                .border(
                                    2.dp,
                                    if (!role.majorRole) Color(114, 116, 244) else Color(
                                        145,
                                        116,
                                        214
                                    ),
                                    RoundedCornerShape(50)
                                )
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (role.majorRole) Color(145, 116, 214) else Color(
                                        25,
                                        25,
                                        44
                                    )
                                )
                                .clickable {
                                    if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                                        showInfo = true
                                    }
                                }
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = role.role,
                                color = if (role.majorRole) Color.White else Color(145, 116, 244),
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .fillMaxHeight()
                            )
                            if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(100))
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.Info,
                                        contentDescription = "Info",
                                        tint = Color(145, 116, 244),
                                        modifier = Modifier
                                            .size(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                roleCount++
                if (device != "Android" || landscapeTablet) roleCount--
            }
        }
        Row {
            roleCount = 0
            roles.forEach { role ->
                if (roleCount >= maxRowCount) {
                    var showInfo by remember { mutableStateOf(false) }
                    if (showInfo && (role.role.contains("Hoster") || role.role.contains("App Developer"))) {
                        AlertDialog(
                            title = {
                                Text(text = "About Role")
                            },
                            text = {
                                Text(text = role.clickPopup)
                            },
                            onDismissRequest = {},
                            confirmButton = {
                                TextButton(
                                    onClick = { showInfo = false }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(2.dp)
                            .height(35.dp)
                            .border(
                                2.dp,
                                if (!role.majorRole) Color(114, 116, 244) else Color(145, 116, 214),
                                RoundedCornerShape(50)
                            )
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (role.majorRole) Color(145, 116, 214) else Color(
                                    25,
                                    25,
                                    44
                                )
                            )
                            .clickable {
                                if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                                    showInfo = true
                                }
                            }
                            .padding(5.dp)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = role.role,
                            color = if (role.majorRole) Color.White else Color(145, 116, 244),
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(2.dp)
                        )
                        if (role.role.contains("Hoster") || role.role.contains("App Developer")) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Info,
                                contentDescription = "Info",
                                tint = if (role.majorRole) Color.White else Color(145, 116, 244),
                                modifier = Modifier
                                    .size(10.dp)
                            )
                        }
                    }
                }

                roleCount++
                if (device != "Android" || landscapeTablet) roleCount--
            }
        }
    }
}