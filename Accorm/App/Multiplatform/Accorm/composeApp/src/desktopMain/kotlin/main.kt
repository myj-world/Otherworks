import accorm.composeapp.generated.resources.Res
import accorm.composeapp.generated.resources.ic
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import network.getResponse
import org.jetbrains.compose.resources.painterResource
import screens.device
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main() = application {
    val windowState = rememberWindowState(
        width = 1280.dp,
        height = 720.dp,
        placement = WindowPlacement.Floating
    )

    val coroutineScope = rememberCoroutineScope()
    var appVersion by remember { mutableStateOf("Loading") }
    coroutineScope.launch {
        try {
            val response =
                getResponse("https://api.github.com/repos/myj-world/Otherworks/releases/latest")
            if (response != null) {
                val json = Json { ignoreUnknownKeys = true }
                val release = json.decodeFromString<release>(response)
                appVersion = release.tag_name
            }
        } catch (_: Exception) {}
    }
    val title = if (appVersion == "Loading") "Accorm Desktop" else "Accorm Desktop - v$appVersion"

    Window(
        onCloseRequest = ::exitApplication,
        title = title,
        state = windowState,
        icon = painterResource(Res.drawable.ic)
    ) {
        App()
    }

//    if (device.contains("Windows")) {
//        val exist = Files.exists(
//            Paths.get(
//                System.getenv("APPDATA"),
//                "Microsoft\\Windows\\Start Menu\\Programs\\Accorm.lnk"
//            )
//        )
//
//        println(exist)
//        if (!exist) {
//            val shortcutPath = Paths.get(
//                System.getenv("APPDATA"),
//                "Microsoft\\Windows\\Start Menu\\Programs\\Accorm.lnk"
//            )
//            val targetPath = "C:\\Program Files\\Accorm\\Accorm.exe"
//            val shortcutContent = """
//        [InternetShortcut]
//        URL=file:///$targetPath
//        IconIndex=0
//        IconFile=$targetPath
//    """.trimIndent()
//
//            Files.write(shortcutPath, shortcutContent.toByteArray(), StandardOpenOption.CREATE)
//        }
//    }
}

@Serializable
data class release (
    val tag_name: String
)