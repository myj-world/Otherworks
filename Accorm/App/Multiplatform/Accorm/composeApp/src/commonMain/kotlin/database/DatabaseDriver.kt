package database

import app.cash.sqldelight.db.SqlDriver

expect object DatabaseDriverFactory {
    fun create(): SqlDriver
}
