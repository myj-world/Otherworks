package screens.accounts

import accounts.LoginStatus
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.gson.stream.JsonReader
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash
import compose.icons.fontawesomeicons.solid.TimesCircle
import compose.icons.fontawesomeicons.solid.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import network.RequestURL
import screens.assets.Contact
import screens.assets.CopyrightMessage
import screens.device
import screens.landscapeTablet
import screens.poppins
import screens.resources.DisplayResourceExternal
import viewmodels.CurrentEmailName
import java.net.URLEncoder
import kotlin.random.Random

object Login : Tab {
    private fun readResolve(): Any = Login
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.User)
            return remember {
                TabOptions(
                    index = 99u,
                    title = "Login",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        if (LoginStatus.getLoginStatus()) {
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(Dashboard)
        }

        var loginResponse by remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        var isLoggingIn by remember { mutableStateOf(false) }

        suspend fun login(email: String, password: String) {
            loginResponse = ""
            val encodedEmail = withContext(Dispatchers.IO) {
                URLEncoder.encode(email, "UTF-8")
            }
            println("Encoded Email: $encodedEmail")

            var encryptionKey =
                RequestURL("https://accorm.ginastic.co/300/login/est/?access-id=3s4w9vd304&email=$encodedEmail")
                    ?: ""
            println("Url: https://accorm.ginastic.co/300/login/est/?access-id=3s4w9vd304&email=$encodedEmail")
            println("Server Response: $encryptionKey")

            encryptionKey = encryptionKey.substring(0..4)
            println("Encryption Key: $encryptionKey")

            try {
                encryptionKey.toLong()
            } catch (e: NumberFormatException) {
                println("Error: ${e.message}")
                loginResponse = "Wrong email"
            }

            if (loginResponse == "") {
                var encryptedPassword = ""

                var encryptionKeyChar = 0
                for (i in password) {
                    val charType =
                        if (i.isUpperCase()) "Upper" else if (i.isLowerCase()) "Lower" else if (i.isDigit()) "Number" else "Special"

                    val modifiedCharCode =
                        i.code + encryptionKey.substring(encryptionKeyChar, encryptionKeyChar + 1)
                            .toInt()

                    val modifiedChar = when {
                        charType == "Upper" && modifiedCharCode > 'Z'.code -> (modifiedCharCode - 25).toChar()
                            .toString()

                        charType == "Lower" && modifiedCharCode > 'z'.code -> (modifiedCharCode - 25).toChar()
                            .toString()

                        charType == "Number" && modifiedCharCode > '9'.code -> (modifiedCharCode - 10).toChar()
                            .toString()

                        charType == "Special" -> i
                        else -> modifiedCharCode.toChar().toString()
                    }

                    encryptedPassword += modifiedChar

                    encryptionKeyChar += 1
                    if (encryptionKeyChar >= encryptionKey.length) {
                        encryptionKeyChar = 0
                    }
                }
                println("Encrypted Password: $encryptedPassword")

                val encodedEncryptedPassword = withContext(Dispatchers.IO) {
                    URLEncoder.encode(encryptedPassword, "UTF-8")
                }
                println("Encoded Encrypted Password: $encodedEncryptedPassword")

                val response =
                    RequestURL("https://accorm.ginastic.co/300/login/?access-id=w943vf3h9&email=$encodedEmail&pswd=$encodedEncryptedPassword")
                        ?: ""
                println("Url 2: https://accorm.ginastic.co/300/login/?access-id=w943vf3h9&email=$encodedEmail&pswd=$encodedEncryptedPassword")
                println("Server Response: $response")

                try {
                    response.toInt()
                    loginResponse = response
                } catch (e: NumberFormatException) {
                    println("Error: ${e.message}")
                    loginResponse = "Wrong password"
                }
            }
        }

        @Composable
        fun retrieveUserData(email: String) {
            LoginStatus.updateLoginStatus(true)
            LoginStatus.updateEmail(email)

            val userId = LoginStatus.getUserID()

            LaunchedEffect(Unit) {
                val userData =
                    RequestURL("https://accorm.ginastic.co/300/login/UserData/?access-id=4954kvti4&unique-id=$userId")?.trim()
                        ?: ""

                delay(1000)

                try {
                    println(userData)
                    val json = Json { ignoreUnknownKeys = true }
                    val loginData = json.decodeFromString<LoginData>(userData)
                    println(loginData)
                    LoginStatus.updateName(loginData.accountData.name)
                    LoginStatus.updateLogoBg(loginData.accountData.colour)
                    LoginStatus.updateLogo(loginData.accountData.name.substring(0, 1))
                    LoginStatus.updateFavourites(loginData.addons.favs)
                    LoginStatus.updateLoginStatus(true)
                    navigator.push(Dashboard)
                } catch (e: Exception) {
                    println(e.message)
                    loginResponse = "Something went wrong"
                    LoginStatus.clearSavedLoginData()
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontFamily = poppins,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(if (device == "Android" && !landscapeTablet) 0.9f else 0.5f)
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
                    .background(Color(0xFF35354a))
                    .padding(20.dp)
            ) {
                var email by remember { mutableStateOf("") }
                var validEmail by remember { mutableStateOf(false) }

                fun validateEmail() {
                    validEmail = email.isNotEmpty()
                            && email.contains("@")
                            && email.contains(".")
                            && email.count { it == '@' } == 1
                            && email.substringAfter('@').length >= 3
                            && email.substringBefore('@').isNotEmpty()
                            && email.reversed().substringBefore('.').isNotEmpty()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                            validateEmail()
                        },
                        label = {
                            Text(
                                text = "Email",
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 16.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color(0xFFacacf9),
                            unfocusedIndicatorColor = Color(0xFFacacf9),
                            disabledIndicatorColor = Color(0xFFacacf9)
                        ),
                        maxLines = 1,
                        enabled = !isLoggingIn
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        imageVector = if (validEmail) FontAwesomeIcons.Solid.CheckCircle else FontAwesomeIcons.Solid.TimesCircle,
                        contentDescription = "Valid Email",
                        modifier = Modifier
                            .size(30.dp),
                        colorFilter = ColorFilter.tint(if (validEmail) Color.Green else Color.Red)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            validateEmail()
                        },
                        label = {
                            Text(
                                text = "Password",
                                color = Color.White,
                                fontFamily = poppins,
                                fontSize = 16.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color(0xFFacacf9),
                            unfocusedIndicatorColor = Color(0xFFacacf9),
                            disabledIndicatorColor = Color(0xFFacacf9)
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        maxLines = 1,
                        enabled = !isLoggingIn
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        imageVector = if (passwordVisible) FontAwesomeIcons.Solid.Eye else FontAwesomeIcons.Solid.EyeSlash,
                        contentDescription = "Show password",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { passwordVisible = !passwordVisible },
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Don't have an account? Sign Up",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            navigator.push(Signup)
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
                var callRetrieveData by remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        isLoggingIn = true
                        loginResponse = ""
                        if (validEmail && password.isNotEmpty()) {
                            coroutineScope.launch {
                                try {
                                    login(email, password)
                                    if (loginResponse.toInt() > 0) {
                                        LoginStatus.updateUserID(loginResponse)
                                        callRetrieveData = true
                                    }
                                } catch (e: Exception) {
                                    println("Error: ${e.message}")
                                    if (loginResponse != "Wrong email" && loginResponse != "Wrong password") {
                                        loginResponse = e.message.toString()
                                    }
                                    isLoggingIn = false
                                }
                            }
                        } else if (!validEmail) {
                            loginResponse = "Invalid email"
                            isLoggingIn = false
                        } else {
                            loginResponse = "Password cannot be empty"
                            isLoggingIn = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp),
                    enabled = !isLoggingIn
                ) {
                    if (!isLoggingIn) {
                        Text(
                            text = "Log In",
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    } else {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                if (callRetrieveData) {
                    retrieveUserData(email)
                }
                Spacer(modifier = Modifier.height(10.dp))
//                if (loginResponse == "Something went wrong" || loginResponse == "Wrong email" || loginResponse == "Wrong password" || loginResponse == "Invalid email" || loginResponse == "Password cannot be empty") {
                if (loginResponse != "") {
                    Text(
                        text = loginResponse,
                        color = Color.Red,
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    isLoggingIn = false
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Serializable
data class LoginData(
    @SerialName("profile") val accountData: AccountData,
    @SerialName("add-ons") val addons: addons
)

@Serializable
data class AccountData(
    @SerialName("name") val name: String,
    @SerialName("colour") val colour: String
)

@Serializable
data class addons(
    @SerialName("favs") val favs: String
)