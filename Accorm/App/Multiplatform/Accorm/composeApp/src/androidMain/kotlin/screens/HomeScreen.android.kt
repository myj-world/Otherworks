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
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

actual val poppins: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.poppins_thin, FontWeight.Thin),
        Font(Res.font.poppins_light, FontWeight.Light),
        Font(Res.font.poppins_bold, FontWeight.Bold),
        Font(Res.font.poppins_semibold, FontWeight.SemiBold),
        Font(Res.font.poppins_regular, FontWeight.Normal)
    )
actual val lexend: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.lexend_thin, FontWeight.Thin),
        Font(Res.font.lexend_regular, FontWeight.Normal),
        Font(Res.font.lexend_bold, FontWeight.Bold)
    )
actual val device: String
    get() = "Android"

actual suspend fun Connected(): Boolean {
    try {
        val deferred = withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url("https://robinjescott.com/").build()
            client.newCall(request).execute().isSuccessful
        }
        return deferred
    } catch (e: Exception) {
        return false
    }
}

actual val landscapeTablet: Boolean
    @Composable
    get() {
        return LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE && LocalConfiguration.current.screenWidthDp > 840
    }