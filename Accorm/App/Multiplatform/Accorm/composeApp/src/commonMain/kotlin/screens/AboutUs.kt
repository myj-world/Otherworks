package screens

import analytics.LogEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Discord
import compose.icons.fontawesomeicons.brands.Instagram
import compose.icons.fontawesomeicons.solid.Ad
import compose.icons.fontawesomeicons.solid.Building
import compose.icons.fontawesomeicons.solid.CaretSquareUp
import compose.icons.fontawesomeicons.solid.Clone
import compose.icons.fontawesomeicons.solid.Code
import compose.icons.fontawesomeicons.solid.FileImport
import compose.icons.fontawesomeicons.solid.Folder
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.Pen
import compose.icons.fontawesomeicons.solid.PenFancy
import compose.icons.fontawesomeicons.solid.PeopleArrows
import compose.icons.fontawesomeicons.solid.PeopleCarry
import compose.icons.fontawesomeicons.solid.PersonBooth
import compose.icons.fontawesomeicons.solid.Trophy
import compose.icons.fontawesomeicons.solid.User
import compose.icons.fontawesomeicons.solid.Wifi
import screens.assets.CopyrightMessage
import screens.assets.Member
import screens.assets.Role
import screens.assets.TeamMember

object AboutUs : Tab {
    private fun readResolve(): Any = AboutUs
    override val options: TabOptions
        @Composable
        get() {
            val title = "Info"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Info)
            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LogEvent("About Us")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
//                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White, fontSize = 24.sp)) {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("Accorm ")
                        }
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Team")
                        }
                    }
                }
            )
            Spacer(Modifier.height(20.dp))
            val developerTeam = listOf(
                TeamMember(
                    name = "M. Musab Khan",
                    imageUrl = "https://accorm.ginastic.co/pfp/musab1.jpg",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Frontend Dev.",
                            roleIcon = FontAwesomeIcons.Solid.PenFancy
                        ),
                        Role(
                            roleName = "Backend Dev.",
                            roleIcon = FontAwesomeIcons.Solid.Code
                        )
                    ),
                    department = "Developers Team",
                    description = "Developed interface & website and content management system."
                ),
                TeamMember(
                    name = "M. Yousuf Jamil",
                    imageUrl = "https://accorm.ginastic.co/pfp/yousuf-jamil.png",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Multiplatform App Dev.",
                            roleIcon = FontAwesomeIcons.Solid.Clone
                        )
                    ),
                    department = "Developers Team",
                    description = "Made the Android & Desktop Accorm apps."
                ),
                TeamMember(
                    name = "M. Abdullah Umair",
                    imageUrl = "https://accorm.ginastic.co/pfp/abd.jpg",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Frontend Dev.",
                            roleIcon = FontAwesomeIcons.Solid.PenFancy
                        ),
                        Role(
                            roleName = "Premier Advisor",
                            roleIcon = FontAwesomeIcons.Solid.PersonBooth
                        )
                    ),
                    department = "Developers Team",
                    description = "Produced the design layout & giving finest advises."
                )
            )

            val contentTeam = listOf(
                TeamMember(
                    name = "Faizan",
                    imageUrl = "https://accorm.ginastic.co/pfp/faizan.png",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Content Manager",
                            roleIcon = FontAwesomeIcons.Solid.PenFancy
                        ),
                        Role(
                            roleName = "Advisor",
                            roleIcon = FontAwesomeIcons.Solid.PersonBooth
                        )
                    ),
                    department = "Content Team",
                    description = "Managing content team & helped the most in the department. Also gave fine advises."
                ),
                TeamMember(
                    name = "Majid M.",
                    imageUrl = "https://accorm.ginastic.co/pfp/majid3.png",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Premier Conducer",
                            roleIcon = FontAwesomeIcons.Solid.Folder
                        ),
                        Role(
                            roleName = "Advisor",
                            roleIcon = FontAwesomeIcons.Solid.PersonBooth
                        )
                    ),
                    department = "Content Team",
                    description = "Contributed most & gave fine advises."
                ),
                TeamMember(
                    name = "Mashal Asim",
                    imageUrl = "https://accorm.ginastic.co/pfp/mashalasim.jpg",
                    tag = FontAwesomeIcons.Solid.Trophy,
                    roles = listOf(
                        Role(
                            roleName = "Conducer",
                            roleIcon = FontAwesomeIcons.Solid.FileImport
                        )
                    ),
                    department = "Content Team",
                    description = "Best contribution to AS Physics"
                ),
                TeamMember(
                    name = "Anas Quzafi",
                    imageUrl = "https://accorm.ginastic.co/pfp/anasquzafi.jpg",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Conducer",
                            roleIcon = FontAwesomeIcons.Solid.FileImport
                        )
                    ),
                    department = "Content Team",
                    description = "Contribution to AS CS"
                )
            )

            val marketingTeam = listOf(
                TeamMember(
                    name = "Muhammad Faiq",
                    imageUrl = "https://accorm.ginastic.co/pfp/faaiq.png",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Promotion Manager",
                            roleIcon = FontAwesomeIcons.Solid.Ad
                        )
                    ),
                    department = "Marketing Team",
                    description = "Promotion Manager"
                ),
                TeamMember(
                    name = "Abdullah Kamil",
                    imageUrl = "https://accorm.ginastic.co/pfp/abdkamil.png",
                    tag = FontAwesomeIcons.Solid.User,
                    roles = listOf(
                        Role(
                            roleName = "Discord Manager",
                            roleIcon = FontAwesomeIcons.Brands.Discord
                        )
                    ),
                    department = "Marketing Team",
                    description = "Setup and control of Accorm discord group."
                ),
                TeamMember(
                    name = "Ali Yousuf",
                    imageUrl = "https://accorm.ginastic.co/pfp/ali-yousuf.jpeg",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Instagram Manager",
                            roleIcon = FontAwesomeIcons.Brands.Instagram
                        )
                    ),
                    department = "Marketing Team",
                    description = "Setup and control of Accorm instagram account."
                ),
                TeamMember(
                    name = "Muhammad Hamza",
                    imageUrl = "https://accorm.ginastic.co/pfp/hamza.jpg",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "External Promoter",
                            roleIcon = FontAwesomeIcons.Solid.CaretSquareUp
                        )
                    ),
                    department = "Marketing Team",
                    description = "From AlWaha International School."
                ),
                TeamMember(
                    name = "Fawwaz Imam",
                    imageUrl = "https://accorm.ginastic.co/pfp/fawwaz-imam.png",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Y10 Proxy",
                            roleIcon = FontAwesomeIcons.Solid.PeopleArrows
                        )
                    ),
                    department = "Marketing Team",
                    description = "Y10 representative of Accorm."
                ),
                TeamMember(
                    name = "Ibrahim Faisal",
                    imageUrl = "https://accorm.ginastic.co/pfp/ibrahim-faisal.png",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Y10 Proxy",
                            roleIcon = FontAwesomeIcons.Solid.PeopleArrows
                        )
                    ),
                    department = "Marketing Team",
                    description = "Y10 representative of Accorm."
                ),
                TeamMember(
                    name = "Abdullah Khan",
                    imageUrl = "https://accorm.ginastic.co/pfp/abd-khan.png",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Y9 Proxy",
                            roleIcon = FontAwesomeIcons.Solid.PeopleArrows
                        )
                    ),
                    department = "Marketing Team",
                    description = "Y9 representative of Accorm."
                ),
                TeamMember(
                    name = "Ayaan Asif",
                    imageUrl = "https://accorm.ginastic.co/pfp/ayaan-asif.png",
                    tag = null,
                    roles = listOf(
                        Role(
                            roleName = "Y9 Proxy",
                            roleIcon = FontAwesomeIcons.Solid.PeopleArrows
                        )
                    ),
                    department = "Marketing Team",
                    description = "Y9 representative of Accorm."
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Text(
                        text = "CEO",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(20.dp))
                }
                item {
                    Member(
                        name = "Musab Khan",
                        imageUrl = "https://accorm.ginastic.co/pfp/musab1.jpg",
                        tag = null,
                        roles = listOf(
                            Role(
                                roleName = "CEO",
                                roleIcon = FontAwesomeIcons.Solid.User
                            ),
                            Role(
                                roleName = "Founder",
                                roleIcon = FontAwesomeIcons.Solid.Building
                            ),
                            Role(
                                roleName = "Hoster",
                                roleIcon = FontAwesomeIcons.Solid.Wifi
                            )
                        ),
                        description = "Developer, goal-chaser, oppurtunity navigator - here to support you."
                    )
                    Spacer(Modifier.height(10.dp))
                }
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Text(
                        text = "Developers Team",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(20.dp))
                }
                items(
                    count = developerTeam.size,
                ) { person ->
                    Member(
                        name = developerTeam[person].name,
                        imageUrl = developerTeam[person].imageUrl,
                        tag = developerTeam[person].tag,
                        roles = developerTeam[person].roles,
                        description = developerTeam[person].description
                    )
                    Spacer(Modifier.height(10.dp))
                }
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Text(
                        text = "Content Team",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(20.dp))
                }
                items(
                    count = contentTeam.size,
                ) { person ->
                    Member(
                        name = contentTeam[person].name,
                        imageUrl = contentTeam[person].imageUrl,
                        tag = contentTeam[person].tag,
                        roles = contentTeam[person].roles,
                        description = contentTeam[person].description
                    )
                    Spacer(Modifier.height(10.dp))
                }
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Text(
                        text = "Marketing Team",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(20.dp))
                }
                items(
                    count = marketingTeam.size,
                ) { person ->
                    Member(
                        name = marketingTeam[person].name,
                        imageUrl = marketingTeam[person].imageUrl,
                        tag = marketingTeam[person].tag,
                        roles = marketingTeam[person].roles,
                        description = marketingTeam[person].description
                    )
                    Spacer(Modifier.height(10.dp))
                }
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
//            Text(
//                text = "Developers Team",
//                color = Color.White,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            Spacer(Modifier.height(10.dp))
//            Person(
//                name = "M. Musab Khan",
//                icon = "https://accorm.ginastic.co/pfp/musab1.jpg",
//                email = "contact@ginastic.co",
//                roles = listOf(
//                    Role(
//                        role = "CEO",
//                        majorRole = true,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Founder",
//                        majorRole = true,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Hoster",
//                        majorRole = true,
//                        clickPopup = "Accorm is hosted on Ginastic (ginastic.co). Musab Khan is the owner of the premium domain ginastic.co."
//                    ),
//                    Role(
//                        role = "Frontend Dev.",
//                        majorRole = false,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Backend Dev.",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "M. Yousuf Jamil",
//                icon = "https://accorm.ginastic.co/pfp/yousuf-jamil.png",
//                email = "muhammadyousufjamil@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "Multiplatform App Developer",
//                        majorRole = true,
//                        clickPopup = "Accorm App for Android, Windows, Linux & Mac is developed by Yousuf Jamil. Accorm is available for download via his account in Google Play Store."
//                    )
//                )
//            )
//            Person(
//                name = "M. Abdullah Umair",
//                icon = "https://accorm.ginastic.co/pfp/abd.jpg",
//                email = "solution.i67@outlook.com",
//                roles = listOf(
//                    Role(
//                        role = "Frontend Dev.",
//                        majorRole = true,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Premier Advisor",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Spacer(Modifier.height(10.dp))
//            Text(
//                text = "Content Team",
//                color = Color.White,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            Spacer(Modifier.height(10.dp))
//            Person(
//                name = "M. Faizan Ali",
//                icon = "https://accorm.ginastic.co/pfp/faizan.png",
//                email = "adamcroft715@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "Content Manager",
//                        majorRole = true,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Advisor",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Majid M.",
//                icon = "https://accorm.ginastic.co/pfp/majid3.png",
//                email = "mylifechoice96@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "Premier Conducer",
//                        majorRole = true,
//                        clickPopup = ""
//                    ),
//                    Role(
//                        role = "Advisor",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Spacer(Modifier.height(20.dp))
//            Text(
//                text = "Marketing Team",
//                color = Color.White,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            Person(
//                name = "Muhammad Faiq",
//                icon = "https://accorm.ginastic.co/pfp/faaiq.png",
//                email = "abdullahkamil1107@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "Promotion Manager",
//                        majorRole = true,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Abdullah Kamil",
//                icon = "https://accorm.ginastic.co/pfp/abdkamil.png",
//                email = "abdullahkamil1107@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "Discord Manager",
//                        majorRole = true,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Ali Yousuf",
//                icon = "https://accorm.ginastic.co/pfp/ali-yousuf.jpeg",
//                email = "",
//                roles = listOf(
//                    Role(
//                        role = "Instagram Manager",
//                        majorRole = true,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Muhammad Hamza",
//                icon = "https://accorm.ginastic.co/pfp/hamza.jpg",
//                email = "abdullahkamil1107@gmail.com",
//                roles = listOf(
//                    Role(
//                        role = "External Promoter",
//                        majorRole = true,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Fawwaz Imam",
//                icon = "https://accorm.ginastic.co/pfp/fawwaz-imam.png",
//                email = "",
//                roles = listOf(
//                    Role(
//                        role = "Y10 Proxy",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Ibrahim Faisal",
//                icon = "https://accorm.ginastic.co/pfp/ibrahim-faisal.png",
//                email = "",
//                roles = listOf(
//                    Role(
//                        role = "Y10 Proxy",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Abdullah Khan",
//                icon = "https://accorm.ginastic.co/pfp/abd-khan.png",
//                email = "",
//                roles = listOf(
//                    Role(
//                        role = "Y9 Proxy",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Person(
//                name = "Ayaan Asif",
//                icon = "https://accorm.ginastic.co/pfp/ayaan-asif.png",
//                email = "",
//                roles = listOf(
//                    Role(
//                        role = "Y9 Proxy",
//                        majorRole = false,
//                        clickPopup = ""
//                    )
//                )
//            )
//            Spacer(Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}