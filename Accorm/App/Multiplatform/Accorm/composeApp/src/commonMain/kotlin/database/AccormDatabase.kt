package database

import com.yousufjamil.accorm.Accorm

object AccormDatabase {
    val driver = DatabaseDriverFactory.create()
    val database = Accorm(driver = driver)
}