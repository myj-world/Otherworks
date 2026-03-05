package viewmodels

import androidx.compose.ui.graphics.painter.BitmapPainter

object CurrentSubject {
    private var subject = ""
    private var url = ""
    private var urlFileName = ""
    private var setLevel = ""
    private var downloadedImgs = listOf<BitmapPainter>()
    private var isDownload = false

    fun getSubject(): String {
        return  subject
    }
    fun setSubject (sub: String) {
        subject = sub
    }

    fun getUrl(): String {
        return  url
    }
    fun setUrl (newUrl: String) {
        url = newUrl
    }

    fun getLevel(): String {
        return setLevel
    }
    fun setLevel (level: String) {
        setLevel = level
    }

    fun getUrlFileName(): String {
        return urlFileName
    }
    fun setUrlFileName (name: String) {
        urlFileName = name
    }

    fun getImages(): List<BitmapPainter> {
        return downloadedImgs
    }
    fun setImages(images: List<BitmapPainter>) {
        downloadedImgs = images
    }

    fun checkIsDownload(): Boolean {
        return isDownload
    }
    fun updateIsDownload(status: Boolean) {
        isDownload = status
    }
}