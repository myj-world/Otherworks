package screens.resources

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.toColorInt

@Composable
actual fun Copy(url: String) {
    val clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Accorm Share Link", url)
    clipboardManager.setPrimaryClip(clip)
}

actual fun parseColor(color: String): Color {
    return try {
        Color(color.toColorInt())
    } catch (e: Exception) {
        Color("#acacf9".toColorInt())
    }
}