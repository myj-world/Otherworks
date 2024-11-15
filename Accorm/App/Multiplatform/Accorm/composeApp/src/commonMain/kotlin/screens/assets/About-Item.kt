package screens.assets

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.requiredWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.MailBulk
import compose.icons.fontawesomeicons.solid.PeopleArrows
import compose.icons.fontawesomeicons.solid.User
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import screens.Connected
import screens.device
import screens.landscapeTablet
import screens.poppins
import viewmodels.CurrentEmailName

@Composable
fun Member(
    name: String,
    imageUrl: String,
    tag: ImageVector?,
    roles: List<Role>,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }

    var connected by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        connected = Connected()
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize()
            .requiredWidth(300.dp)
            .height(if (!expanded) 275.dp else 315.dp)
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
            .clickable { expanded = !expanded }
            .padding(15.dp),
    ) {
        if (tag != null) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = tag,
                    contentDescription = "Tag",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (connected) {
                val resource = asyncPainterResource(imageUrl)
                when (resource) {
                    is Resource.Success -> {
                        KamelImage(
                            resource = asyncPainterResource(imageUrl),
                            contentDescription = name,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .size(40.dp)
                                .clip(RoundedCornerShape(100)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> {
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
                }
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
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(vertical = 10.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            for (role in roles) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = role.roleIcon,
                        contentDescription = role.roleName,
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = role.roleName,
                        color = Color.White,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (expanded) {
                Text(
                    text = description,
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}