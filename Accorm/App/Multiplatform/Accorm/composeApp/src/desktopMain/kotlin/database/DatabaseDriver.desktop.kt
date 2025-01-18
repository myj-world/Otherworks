package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.yousufjamil.accorm.Accorm
import java.io.File

actual object DatabaseDriverFactory {
    actual fun create(): SqlDriver {
//        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val dbFile = File(System.getProperty("user.home"), "/Accorm/Database/accorm.db")
        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:${dbFile.absolutePath}")
        if (!dbFile.exists()) {
            dbFile.parentFile.mkdirs()
            dbFile.createNewFile()
            Accorm.Schema.create(driver)
        }
        return driver
    }
}