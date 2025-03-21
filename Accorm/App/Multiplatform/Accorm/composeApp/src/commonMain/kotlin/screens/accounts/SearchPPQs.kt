package screens.accounts

import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import java.time.Year

suspend fun searchPPQs(
    range: IntRange,
    subject: String,
    level: String,
    subjectCode: String,
    component: Int,
    variantCodes: List<String>,
    query: String
): List<PPQ> {
    //    Generate urls
    val list = MutableList(0) { PPQ("", Year.now(), "", "", "", "", "","", "") }
    val qpUrls = mutableListOf<String>()
    val sessionCodes = mutableListOf<String>()
    val paperCodes = mutableListOf<String>()
    val componentCodes = mutableListOf<String>()
    val years = mutableListOf<String>()
    var loop = false // To repeat loop to check both winter and summer
    for (i in range) {
        var session = "s"
        val year = i.toString().substring(2..3)
        for (j in variantCodes) {
            val code = subjectCode + "_" + session + year + "_qp_$component$j"
            val url =
                "https://pastpapers.papacambridge.com/directories/CAIE/CAIE-pastpapers/upload/$code.pdf"
            qpUrls.add(url)

            sessionCodes.add(session)
            years.add(year)
            paperCodes.add(code)
            componentCodes.add("$component$j")
        }

        session = "w"
        for (j in variantCodes) {
            val code = subjectCode + "_" + session + year + "_qp_$component$j"
            val url =
                "https://pastpapers.papacambridge.com/directories/CAIE/CAIE-pastpapers/upload/$code.pdf"
            qpUrls.add(url)

            sessionCodes.add(session)
            years.add(year)
            paperCodes.add(code)
            componentCodes.add("$component$j")
        }
    }

    // Execute each url asynchronously
    // Launch a coroutine scope
    coroutineScope {
        // Launch async tasks for each URL concurrently on the IO dispatcher.
        val deferredResults = qpUrls.mapIndexed { i, url ->
            async(Dispatchers.IO) {
                val session = sessionCodes[i]
                val year = years[i]
                val code = paperCodes[i]
                println(url)
                try {
                    val response = HttpClient().get(url) {
                        onDownload { bytesSentTotal, contentLength ->
                            println("Downloaded $bytesSentTotal of $contentLength")
                        }
                    }.bodyAsChannel().toByteArray()
                    val pdf = Loader.loadPDF(response)
                    val pdfTextStripper = PDFTextStripper()
                    val text = pdfTextStripper.getText(pdf)
                    if (text.contains(query)) {
                        // Instead of adding to a shared list here, return the value
                        PPQ(
                            session = session,
                            year = Year.parse(year),
                            level = level,
                            subject = subject,
                            subjectCode = subjectCode,
                            codeName = code,
                            qpLink = url,
                            msLink = url.replace("qp", "ms"),
                            componentCode = componentCodes[i]
                        )
                    } else {
                        null // if not matching, return null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }

        // Wait for all async tasks to complete and filter out null results.
        val results = deferredResults.awaitAll().filterNotNull().distinct()
        list.addAll(results)
    }

    return list
}

data class PPQ(
    val session: String,
    val year: Year,
    val level: String,
    val componentCode: String,
    val subject: String,
    val subjectCode: String,
    val codeName: String,
    val qpLink: String,
    val msLink: String
)