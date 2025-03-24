package screens.assets

import korlibs.audio.sound.NativeSoundProvider
import korlibs.audio.sound.Sound
import korlibs.datastructure.pauseable.pause
import korlibs.datastructure.pauseable.resume
import network.RequestURL
import java.io.File

actual class AudioPlayer {
//    var player: Clip? = null
    val audioFile = File.createTempFile("audio", ".mp3")
    var sound: Sound? = null
    val player = NativeSoundProvider()

    actual suspend fun downloadAudio(url: String): Boolean {
        try {
            val response = RequestURL(url) ?: throw Exception("Failed to download audio")
//            delay(Duration.ofMillis(5000L))
            audioFile.writeBytes(response.toByteArray())
//            withContext(Dispatchers.IO) {
//                val file = AudioSystem.getAudioInputStream(audioFile)
//                player = AudioSystem.getClip()
//                player?.open(file)
//            }
            sound = player.createSound(response.toByteArray())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    actual suspend fun playAudio(): Boolean {
//        player?.start()
        try {
            if (sound == null) throw Exception("Player is null")
            player.playAndWait(sound!!.toStream())
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    actual fun audioState(): Boolean {
//        return player?.isActive ?: false
        return !player.paused
    }

    actual fun pauseAudio() {
//        player?.stop()
        player.pause()
    }

    actual fun resumeAudio() {
//        player?.start()
        player.resume()
    }

    actual fun stopAudio() {
//        player?.stop()
//        player?.close()
        player.pause()
        player.dispose()
        audioFile.delete()
    }
}