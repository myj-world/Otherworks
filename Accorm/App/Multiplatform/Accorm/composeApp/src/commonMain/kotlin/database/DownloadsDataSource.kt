package database

import com.yousufjamil.accorm.Accorm
import com.yousufjamil.accorm.Downloads

class DownloadsDataSource (private val db: Accorm) {
    private val queries = db.downloadsQueries

    fun insertDownload(
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
        file1nameondisk: String,
        file2nameondisk: String
    ) {
        queries.addDownload(
            downlaodid = null,
            uniqueid = uniqueid.toLong(),
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
            file1nameondisk = file1nameondisk,
            file2nameondisk = file2nameondisk
        )
    }

    fun retrieveSpecificDownloadData(idToFind: Int): List<Downloads> {
        return queries.retrieveSpecificDownloadData(idToFind.toLong()).executeAsList()
    }

    fun retrieveAllDownloads(): List<Downloads> {
        return queries.retrieveAllDownloads().executeAsList()
    }

    fun deleteDownload(idToDelete: Int) {
        queries.deleteDownload(idToDelete.toLong())
    }

    fun checkDownloadExists(idToFind: Int): Boolean {
        return queries.checkDownloadExists(idToFind.toLong()).executeAsOne() > 0
    }
}