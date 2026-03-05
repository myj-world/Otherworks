package screens.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Composable
actual fun Copy (url: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(url), null)
}

actual fun parseColor(color: String): Color {
    val colorMod = java.awt.Color.decode(color)
    return Color(colorMod.red, colorMod.green, colorMod.blue)
}