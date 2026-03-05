package viewmodels

object CurrentEmailName {
    private var email = ""
    private var name = ""
    fun getEmail(): String {
        return  email
    }
    fun setEmail (mail: String) {
        email = mail
    }

    fun getName(): String {
        return  name
    }
    fun setName (namePer: String) {
        name = namePer
    }
}