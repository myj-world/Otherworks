@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package screens.assets

expect class AudioPlayer() {
    suspend fun downloadAudio(url: String): Boolean
    suspend fun playAudio(): Boolean
    fun audioState(): Boolean
    fun pauseAudio()
    fun resumeAudio()
    fun stopAudio()
}