package screens.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

@Composable
actual fun launchURL(url: String): Boolean {
    val uriHandler = LocalUriHandler.current
    return try {
        uriHandler.openUri(url)
        true
    } catch (e: Exception) {
        false
    }
}