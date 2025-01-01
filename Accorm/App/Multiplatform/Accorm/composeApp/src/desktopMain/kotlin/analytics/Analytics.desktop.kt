package analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import network.getResponse

@Composable
actual fun LogEvent(
    name: String,
    uniqueId: Int?,
    resourceName: String?
) {
    LaunchedEffect(Unit) {
        if (uniqueId != null) getResponse("https://example.com")
    }
}