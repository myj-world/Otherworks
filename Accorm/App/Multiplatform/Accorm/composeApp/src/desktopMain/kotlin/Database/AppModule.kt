package Database

import com.yousufjamil.accorm.Accorm

class DesktopAppModule : AppModule {
    private val db by lazy {
        Accorm(
            driver = DatabaseDriverFactory().create()
        )
    }

    override fun provideDataSource() = DataSource(db)
}