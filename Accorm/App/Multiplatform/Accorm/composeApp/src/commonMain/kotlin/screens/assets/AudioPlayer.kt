package screens.assets

import androidx.compose.runtime.Composable

expect class AudioPlayer() {
    suspend fun downloadAudio(url: String): Boolean
    suspend fun playAudio(): Boolean
    fun audioState(): Boolean
    fun pauseAudio()
    fun resumeAudio()
    fun stopAudio()
}