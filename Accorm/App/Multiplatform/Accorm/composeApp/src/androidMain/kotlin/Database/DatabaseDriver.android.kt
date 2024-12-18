package Database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.yousufjamil.accorm.Accorm

actual class DatabaseDriverFactory (private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(Accorm.Schema, context, "Accorm.db")
    }
}