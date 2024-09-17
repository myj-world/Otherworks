package analytics

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize

@Composable
actual fun LogEvent(
    name: String
) {
    val nameMod = "${name.replace(" ", "_")}_Accorm_Android"

    Firebase.initialize(LocalContext.current)
    val analytics = Firebase.analytics
    analytics.logEvent(nameMod, null)

    println("Logged event: $nameMod")
}