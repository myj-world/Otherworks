package analytics

import androidx.compose.runtime.Composable

@Composable
expect fun LogEvent(
    name: String,
    uniqueId: Int?,
    subject: String?,
)