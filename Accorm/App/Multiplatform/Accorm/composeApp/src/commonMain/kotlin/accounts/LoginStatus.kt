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
        return settings.getString("userID", "0")
    }




    fun updateEmail(email: String) {
        settings.putString("email", email)
    }

    fun getEmail(): String {
        return settings.getString("email", "error@example.com")
    }



    fun updateName(name: String) {
        settings.putString("name", name)
    }

    fun getName(): String {
        return settings.getString("name", "Error")
    }



    fun updateLogo(logo: String) {
        settings.putString("logo", logo)
    }

    fun getLogo(): String {
        return settings.getString("logo", "E")
    }



    fun updateLogoBg(logoBg: String) {
        settings.putString("ulogobg", logoBg)
    }

    fun getLogoBg(): String {
        return settings.getString("ulogobg", "#FF0000")
    }



    fun updateFavourites(favourites: String) {
        settings.putString("favourites", favourites)
    }

    fun getFavourites(): String {
        return settings.getString("favourites", "")
    }



    fun clearSavedLoginData(): Boolean {
        settings.remove("loggedIn")
        settings.remove("userID")
        settings.remove("email")
        settings.remove("name")
        settings.remove("logo")
        settings.remove("ulogobg")
        settings.remove("favourites")
        return true
    }
}