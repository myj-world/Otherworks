package database

import com.yousufjamil.accorm.Accorm

object AccormDatabase {
    val database = Accorm(driver = DatabaseDriverFactory.create())
}