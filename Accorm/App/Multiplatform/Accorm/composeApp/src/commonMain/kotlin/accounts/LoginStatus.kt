package accounts

import com.russhwolf.settings.Settings

object LoginStatus {
    //    Store the login details of the user
    val settings: Settings = Settings()

    fun updateLoginStatus(status: Boolean) {
        settings.putBoolean("loggedIn", status)
    }

    fun getLoginStatus(): Boolean {
        return settings.getBoolean("loggedIn", false)
    }



    fun updateUserID(userID: String) {
        settings.putString("userID", userID)
    }

    fun getUserID(): String {
        return settings.getString("userID", "")
    }




    fun updateEmail(email: String) {
        settings.putString("email", email)
    }

    fun getEmail(): String {
        return settings.getString("email", "")
    }



    fun updateName(name: String) {
        settings.putString("name", name)
    }

    fun getName(): String {
        return settings.getString("name", "")
    }



    fun updateLogo(logo: String) {
        settings.putString("logo", logo)
    }

    fun getLogo(): String {
        return settings.getString("logo", "")
    }



    fun updateLogoBg(logoBg: String) {
        settings.putString("ulogobg", logoBg)
    }

    fun getLogoBg(): String {
        return settings.getString("ulogobg", "")
    }



    fun clearSavedData(): Boolean {
        settings.clear()
        return true
    }
}