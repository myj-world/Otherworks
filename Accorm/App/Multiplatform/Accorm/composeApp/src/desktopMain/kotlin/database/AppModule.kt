package database

import com.yousufjamil.accorm.Accorm

class DesktopAppModule : AppModule {
    private val db by lazy {
        Accorm(
            driver = DatabaseDriverFactory.create()
        )
    }

    override fun provideDownloadsDataSource() = DownloadsDataSource(db)
    override fun provideAccountsDataSource() = AccountsDataSource(db)
    override fun provideHistoryDataSource() = HistoryDataSource(db)
}