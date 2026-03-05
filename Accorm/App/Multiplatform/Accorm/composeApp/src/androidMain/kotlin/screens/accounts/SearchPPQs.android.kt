package screens.accounts

import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.ParcelFileDescriptor
import java.io.File

actual fun getAndroidSearch(respose: ByteArray, keyword: String): Boolean {
    var toReturn = false
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        throw UnsupportedOperationException("Android version not supported")
    } else {
        val file = File.createTempFile("tempSearch", "pdf")
        file.deleteOnExit()
        file.writeBytes(respose)
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdf = PdfRenderer(fileDescriptor)
        for (page in 0 until pdf.pageCount) {
            val pdfPage = pdf.openPage(page)
            val content = pdfPage.textContents
            var text = ""
            content.forEach { it ->
                text += it.text
            }
            if (text.contains(keyword)) {
                println("Found")
                pdfPage.close()
                toReturn = true
                break
            }
            println(content)
            pdfPage.close()
        }
        pdf.close()
        fileDescriptor.close()
        file.delete()
        return toReturn
    }
}