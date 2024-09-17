package viewmodels

object CurrentSubject {
    private var subject = ""
    private var url = ""
    private var urlFileName = ""
    private var setLevel = ""

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
}