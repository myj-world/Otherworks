package database

interface AppModule {
    fun provideDownloadsDataSource(): DownloadsDataSource
    fun provideAccountsDataSource(): AccountsDataSource
    fun provideHistoryDataSource(): HistoryDataSource
}