package network

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun getResponse(url: String): String? {
    try {
        val response = withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().body().string()
        }
        return response
    } catch (e: Exception) {
        return null
    }
}