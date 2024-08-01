package viewmodels

object CurrentSubject {
    private var subject = ""
    fun getSubject(): String {
        return  subject
    }
    fun setSubject (sub: String) {
        subject = sub
    }
}