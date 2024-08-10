package network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

actual suspend fun getResponse(url: String): String? {
    val client = HttpClient()
    return try {
        val response = client.get(url)
        response.bodyAsText()
    } catch (e: Exception) {
        null
    }
}