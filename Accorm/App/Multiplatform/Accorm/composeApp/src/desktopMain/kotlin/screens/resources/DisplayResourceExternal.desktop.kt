package screens.resources

import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.net.URI

@Composable
actual fun launchURL(url: String): Boolean {
    return try {
        val desktop = Desktop.getDesktop()
        desktop.browse(URI.create(url))
        true
    } catch (e: Exception) {
        println(e)
        false
    }
}