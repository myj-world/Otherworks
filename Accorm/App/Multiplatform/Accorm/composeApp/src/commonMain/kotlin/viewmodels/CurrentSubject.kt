package viewmodels

object CurrentSubject {
    private var subject = ""
    private var url = ""
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
}