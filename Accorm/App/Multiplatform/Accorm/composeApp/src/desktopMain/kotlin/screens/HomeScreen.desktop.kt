package screens

import androidx.compose.ui.text.font.FontFamily
import fonts.Fonts
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.net.InetSocketAddress
import java.net.Socket

actual val poppins: FontFamily
    get() = Fonts().poppins
actual val lexend: FontFamily
    get() = Fonts().lexend
actual val device: String
    get() = System.getProperty("os.name")

actual suspend fun Connected(): Boolean {
    val client = HttpClient() {}
    return try {
        val response = client.get("https://robinjescott.com/")
        response.status.value == 200
    } catch (e: Exception) {
        false
    }
}