import accorm.composeapp.generated.resources.Res
import accorm.composeapp.generated.resources.ic
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
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

    Window(
        onCloseRequest = ::exitApplication,
        title = "Accorm",
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