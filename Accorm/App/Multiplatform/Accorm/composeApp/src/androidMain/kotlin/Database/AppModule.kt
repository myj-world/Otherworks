package Database

import android.content.Context
import com.yousufjamil.accorm.Accorm

class AndroidAppModule(
    private val context: Context,
) : AppModule {
    private val db by lazy {
        Accorm(
            driver = DatabaseDriverFactory(context).create()
        )
    }

    override fun provideDataSource() = DataSource(db)
}