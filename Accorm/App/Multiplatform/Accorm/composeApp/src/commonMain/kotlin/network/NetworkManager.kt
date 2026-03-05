package network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun RequestURL(url: String): String? {
    return getResponse(url)
}

expect suspend fun getResponse(url: String): String?