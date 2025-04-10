package database

import com.yousufjamil.accorm.Accorm
import com.yousufjamil.accorm.Accounts

class AccountsDataSource (private val db: Accorm) {
    private val queries = db.accountsQueries

    fun getUser(): List<Accounts> {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getUser().executeAsList()
    }


    fun initializeForNoUser() {
        queries.initializeForNoUsers()
    }


    fun clearSavedLoginData() {
        queries.clearSavedLoginData()
    }


    fun getLoginStatus(): Boolean {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getLoginStatus().executeAsOne()
    }

    fun updateLoginStatus(status: Boolean) {
        queries.updateLoginStatus(status)
    }


    fun getUserID(): Int {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getUserID().executeAsOne().toInt()
    }

    fun updateUserID(userID: Int) {
        queries.updateUserID(userID.toLong())
    }


    fun getEmail(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getEmail().executeAsOne()
    }

    fun updateEmail(email: String) {
        queries.updateEmail(email)
    }


    fun getName(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getName().executeAsOne()
    }

    fun updateName(name: String) {
        queries.updateName(name)
    }


    fun getLogo(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getLogo().executeAsOne()
    }

    fun updateLogo(logo: String) {
        queries.updateLogo(logo)
    }


    fun getLogoBg(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getLogoBg().executeAsOne()
    }

    fun updateLogoBg(logoBg: String) {
        queries.updateLogoBg(logoBg)
    }


    fun getFavourites(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }

        return queries.getFavourites().executeAsOne()
    }

    fun updateFavourites(favourites: String) {
        queries.updateFavourites(favourites)
    }


    fun getTheme(): String {
        try {
            queries.getLoginStatus().executeAsOne()
        } catch (e: Exception) {
            initializeForNoUser()
        }
        return queries.getTheme().executeAsOne().theme ?: ""
    }

    fun setTheme(theme: String) {
        queries.setTheme(theme)
    }
}