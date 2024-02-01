package com.yousufjamil.accormadmin

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class BackgroundWorker(val context: Context): AsyncTask<String, Void, String>() {

    var response = ""
    override fun doInBackground(vararg params: String?): String {
        val urlToConnect = params[0]
        val content = StringBuffer()
        try {
            val url = URL(urlToConnect)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8"
            )
            con.doOutput = true

            val outputStream = con.outputStream
            val wr = OutputStreamWriter(outputStream)
            wr.flush()

            val recieve = BufferedReader(InputStreamReader(con.inputStream))
            var inputLine: String?

            while (recieve.readLine().also { inputLine = it } != null) {
                content.append(inputLine)
            }
            recieve.close()

            con.disconnect()
        } catch (e: IOException) {
            println("Error found in connecting to database: $e")
        } catch (e: MalformedURLException) {
            println("Error found in connecting to database: $e")
        }

        response = content.toString()
        return content.toString()
    }
}