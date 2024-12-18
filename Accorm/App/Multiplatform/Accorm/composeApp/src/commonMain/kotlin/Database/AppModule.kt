package Database

interface AppModule {
    fun provideDataSource(): DataSource
}