@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package screens.assets

import android.media.MediaPlayer
import network.RequestURL
import java.io.File

actual class AudioPlayer {
    val player = MediaPlayer()
    val audioFile = File.createTempFile("audio", ".mp3")

    actual suspend fun downloadAudio(url: String): Boolean {
        val response: String?
        try {
            response = RequestURL(
                url = url
            )
            if (response == null) throw Exception("Failed to download audio")
            audioFile.writeBytes(response.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    actual suspend fun playAudio(): Boolean {
        try {
            player.setDataSource(audioFile.absolutePath)
            player.prepare()
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    actual fun audioState(): Boolean {
        return player.isPlaying
    }

    actual fun pauseAudio() {
        player.pause()
    }

    actual fun resumeAudio() {
        player.start()
    }

    actual fun stopAudio() {
        player.stop()
        player.reset()
        player.release()
        audioFile.delete()
    }
}