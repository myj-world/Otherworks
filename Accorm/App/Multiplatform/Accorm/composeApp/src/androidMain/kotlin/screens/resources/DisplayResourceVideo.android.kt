package screens.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

@Composable
actual fun launchURL(url: String): Boolean {
    val uriHandler = LocalUriHandler.current
    uriHandler.openUri(url)
    return true
}