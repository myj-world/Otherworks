package screens

import accorm.composeapp.generated.resources.Res
import accorm.composeapp.generated.resources.lexend_bold
import accorm.composeapp.generated.resources.lexend_regular
import accorm.composeapp.generated.resources.lexend_thin
import accorm.composeapp.generated.resources.poppins_bold
import accorm.composeapp.generated.resources.poppins_light
import accorm.composeapp.generated.resources.poppins_regular
import accorm.composeapp.generated.resources.poppins_semibold
import accorm.composeapp.generated.resources.poppins_thin
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.jetbrains.compose.resources.Font
import java.net.InetSocketAddress
import java.net.Socket

actual val poppins: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.poppins_thin,
            weight = FontWeight.Thin
        ),
        Font(
            resource = Res.font.poppins_light,
            weight = FontWeight.Light
        ),
        Font(
            resource = Res.font.poppins_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.poppins_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.poppins_bold,
            weight = FontWeight.Bold
        )
    )
actual val lexend: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.lexend_thin,
            weight = FontWeight.Thin
        ),
        Font(
            resource = Res.font.lexend_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.lexend_bold,
            weight = FontWeight.Bold
        )
    )
actual val device: String
    get() = System.getProperty("os.name")

actual suspend fun Connected(): Boolean {
    val client = HttpClient()
    return try {
        val response = client.get("https://robinjescott.com/")
        response.status.value == 200
    } catch (e: Exception) {
        false
    }
}

actual val landscapeTablet: Boolean
    @Composable
    get() = false