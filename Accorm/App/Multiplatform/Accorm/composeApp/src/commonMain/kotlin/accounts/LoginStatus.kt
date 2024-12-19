package accounts

import com.yousufjamil.accorm.Accounts
import database.AccormDatabase
import database.AccountsDataSource

object LoginStatus {
    //    Store the login details of the user

    val db = AccountsDataSource(AccormDatabase.database)

    fun updateLoginStatus(status: Boolean) {
        db.updateLoginStatus(status)
    }

    fun getLoginStatus(): Boolean {
        return db.getLoginStatus()
    }



    fun updateUserID(userID: String) {
        db.updateUserID(userID.toInt())
    }

    fun getUserID(): String {
        return db.getUserID().toString()
    }




    fun updateEmail(email: String) {
        db.updateEmail(email)
    }

    fun getEmail(): String {
        return db.getEmail()
    }



    fun updateName(name: String) {
        db.updateName(name)
    }

    fun getName(): String {
        return db.getName()
    }



    fun updateLogo(logo: String) {
        db.updateLogo(logo)
    }

    fun getLogo(): String {
        return db.getLogo()
    }



    fun updateLogoBg(logoBg: String) {
        db.updateLogoBg(logoBg)
    }

    fun getLogoBg(): String {
        return db.getLogoBg()
    }



    fun updateFavourites(favourites: String) {
        db.updateFavourites(favourites)
    }

    fun getFavourites(): String {
        return db.getFavourites()
    }



    fun clearSavedLoginData(): Boolean {
        db.clearSavedLoginData()
        return true
    }


    fun initializeForNoUser(): Boolean {
        db.initializeForNoUser()
        return true
    }

    fun getUser(): List<Accounts> {
        return db.getUser()
    }
}