package screens.resources

import database.AccormDatabase
import database.DownloadsDataSource

suspend fun DownloadToInternal(
    uniqueid: Int,
    title: String,
    subject: String,
    type: String,
    publisher: String,
    publisherlogobg: String,
    publisherlogo: String,
    description: String,
    specification: String,
    chapter: String,
    author: String,
    authorcrediturl: String,
    publisheddate: String,
    link: String
) {
    val db = AccormDatabase.database
    val download = DownloadsDataSource(db = db)

    if (!download.checkDownloadExists(uniqueid)) {

        val data: List<String>
        try {
            data = FileManager.downloadToAppStorage(link = link)
            if (data.isEmpty()) {
                println("Download failed")
                return
            }
        } catch (e: Exception) {
            println(e.message)
            return
        }

        download.insertDownload(
            uniqueid = uniqueid,
            title = title,
            subject = subject,
            type = type,
            publisher = publisher,
            publisherlogobg = publisherlogobg,
            publisherlogo = publisherlogo,
            description = description,
            specification = specification,
            chapter = chapter,
            author = author,
            authorcrediturl = authorcrediturl,
            publisheddate = publisheddate,
            file1nameondisk = data[0],
            file2nameondisk = data[1]
        )
    } else {
        throw Exception("Download already exists")
    }
}

suspend fun launchDownload(
    uniqueid: Int
) {
    val db = AccormDatabase.database
    val download = DownloadsDataSource(db = db)
    if (download.checkDownloadExists(idToFind = uniqueid)) {
        val downloadData = download.retrieveSpecificDownloadData(idToFind = uniqueid)
        println("Download Exists $downloadData")

        FileManager.loadFileFromStorage(
            name = downloadData[0].title,
            file1 = downloadData[0].file1nameondisk,
            file2 = downloadData[0].file2nameondisk
        )

    } else {
        println("Download does not exist")
        throw Exception("Download does not exist")
    }
}

suspend fun deleteDownload(
    uniqueid: Int
) {
    val db = AccormDatabase.database
    val download = DownloadsDataSource(db = db)
    val downloadData = download.retrieveSpecificDownloadData(idToFind = uniqueid)
    FileManager.deleteFileFromStorage(
        file1 = downloadData[0].file1nameondisk,
        file2 = downloadData[0].file2nameondisk
    )
    download.deleteDownload(idToDelete = uniqueid)
    println("Download Deleted")
}

expect object FileManager {
    suspend fun downloadToAppStorage(link: String): List<String>
    suspend fun loadFileFromStorage(name: String, file1: String, file2: String)
    suspend fun deleteFileFromStorage(file1: String, file2: String)
}