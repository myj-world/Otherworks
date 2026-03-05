package analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import network.getResponse

@Composable
actual fun LogEvent(
    name: String,
    uniqueId: Int?,
    subject: String?
) {
    LaunchedEffect(Unit) {
        if (uniqueId != null) getResponse("https://accorm.ginastic.co/300/analytics/opens/?access-id=594igfk54&subject=$subject&unique-id=$uniqueId")
    }
}