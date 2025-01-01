package analytics

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize
import network.getResponse

@Composable
actual fun LogEvent(
    name: String,
    uniqueId: Int?,
    resourceName: String?
) {
    LaunchedEffect(Unit) {
        if (uniqueId != null) getResponse("")
    }
    val nameMod = "${name.replace(" ", "_")}_Accorm_Android"

    Firebase.initialize(LocalContext.current)
    val analytics = Firebase.analytics
    analytics.logEvent(nameMod, null)

    println("Logged event: $nameMod")
}