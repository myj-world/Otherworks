package database

import com.yousufjamil.accorm.Accorm
import com.yousufjamil.accorm.History

class HistoryDataSource (private val db: Accorm) {
    private val queries = db.historyQueries

    fun getHistory(): List<History> {
        return queries.getHistory().executeAsList()
    }

    fun addToHistory(
        uniqueId: String,
        subjectRetrieve: String,
        logo: String,
        logoBg: String,
        chapter: String,
        publisher: String,
        title: String,
        description: String,
        specification: String,
        published: String,
        url: String,
        source: String,
        credit: String,
        creditUrl: String,
        backgroundColor: String,
        textColor: String,
        labelColor: String,
        logoTextColour: String,
        downloadIconColor: String,
        isDownload: Boolean,
        contentType: String
    ) {
        queries.addToHistory(
            historyId = null,
            uniqueId = uniqueId,
            subjectRetrieve = subjectRetrieve,
            logo = logo,
            logoBg = logoBg,
            chapter = chapter,
            publisher = publisher,
            title = title,
            description = description,
            specification = specification,
            published = published,
            url = url,
            source = source,
            credit = credit,
            creditUrl = creditUrl,
            backgroundColor = backgroundColor,
            textColor = textColor,
            labelColor = labelColor,
            logoTextColour = logoTextColour,
            downloadIconColor = downloadIconColor,
            isDownload = isDownload.toString(),
            contentType = contentType
        )
    }
}