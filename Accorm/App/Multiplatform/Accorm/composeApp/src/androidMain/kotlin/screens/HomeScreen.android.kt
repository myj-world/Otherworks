package screens

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
//import com.squareup.okhttp.OkHttpClient
//import com.squareup.okhttp.Request
import com.yousufjamil.accorm.MainActivity
import com.yousufjamil.accorm.R
import fonts.Fonts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

actual val poppins: FontFamily
    get() = Fonts().poppins
actual val lexend: FontFamily
    get() = Fonts().lexend
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