package database

import android.annotation.SuppressLint
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.yousufjamil.accorm.Accorm
import com.yousufjamil.accorm.MainActivity

@SuppressLint("StaticFieldLeak")
actual object DatabaseDriverFactory {
    lateinit var context: Context

    fun initContext(context: Context) {
        this.context = context
    }

    actual fun create(): SqlDriver {
        println("Context: $context")
        return AndroidSqliteDriver(Accorm.Schema, context, "Accorm.db")
    }
}