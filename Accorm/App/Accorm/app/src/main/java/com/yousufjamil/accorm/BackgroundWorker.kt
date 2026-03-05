package com.yousufjamil.accorm

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

class BackgroundWorker : AsyncTask<String, Void, String>() {

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

            if (con.responseCode == 200) {

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

                response = content.toString()
            } else {
                response = "{'0':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, '1':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, 'num-of-rows': 1}"
            }
        } catch (e: IOException) {
            println("Error found in connecting to database: $e")
            response = "{'0':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, '1':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, 'num-of-rows': 1}"
        } catch (e: MalformedURLException) {
            println("Error found in connecting to database: $e")
            response = "{'0':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, '1':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, 'num-of-rows': 1}"
        } catch (e: Exception) {
            println("Error found in connecting to database: $e")
            response = "{'0':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, '1':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, 'num-of-rows': 1}"
        }

//        TO BRING BACK
        if (!params[0]?.contains("200")!!) {
            response =
                "{'0':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, '1':{'logo_bg': '#000000', 'logo': 'A', 'publisher': 'Accorm', 'title': 'Sample', 'fileID': '1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0', 'link': 'https://www.youtube.com/watch?v=rmfezsIlZfA', 'unique_id': 'abcdefghijklmnopqrstuvwxyz', 'date': '15-07-24', 'logo-col': '#000000', 'pfp-name': 'A', 'name': 'Accorm', 'notes': '1', 'videos': '1', 'blogs': '1', 'Type': 'Files', 'Name': 'Accorm', 'URL': 'https://drive.google.com/file/d/1rwpOxiMpOexSAA8IYkXfqu3k_OuHTBf0/view'}, 'num-of-rows': 1}"
        } else {
            response = "{\n" +
                    "    \"may/jun/qp\": {\n" +
                    "        \"Name\": \"qp\",\n" +
                    "        \"Type\": \"Folder\",\n" +
                    "        \"URL\": \"https://drive.google.com/drive/folders/1DXTgeadexRTeGGE_yqAm4wFNiuHDMirS\",\n" +
                    "        \"Size\": 0\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s19_qp_11.pdf\": {\n" +
                    "        \"Name\": \"0493_s19_qp_11.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/14fnFqUBnvn-JzrUCdu7gkbpSkQ4eHJu3/view?usp=drivesdk\",\n" +
                    "        \"Size\": 1109.9501953125\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s20_qp_11.pdf\": {\n" +
                    "        \"Name\": \"0493_s20_qp_11.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1VAOswJU6pP01VIeyN9ZVorndWn-2DPeX/view?usp=drivesdk\",\n" +
                    "        \"Size\": 991.654296875\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s23_qp_11.pdf\": {\n" +
                    "        \"Name\": \"0493_s23_qp_11.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1YVPTOsTdrYDc7Uqrpa0FxOnv3lDsWl5W/view?usp=drivesdk\",\n" +
                    "        \"Size\": 718.90625\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s19_qp_21.pdf\": {\n" +
                    "        \"Name\": \"0493_s19_qp_21.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1_vHBV8C9v9nPseshDC5-41nXA0T1YrpZ/view?usp=drivesdk\",\n" +
                    "        \"Size\": 2761.068359375\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s21_qp_21.pdf\": {\n" +
                    "        \"Name\": \"0493_s21_qp_21.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1cbnyPGmORuBHqwbhy9E_3c81CA7uTWo1/view?usp=drivesdk\",\n" +
                    "        \"Size\": 977.2158203125\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s20_qp_21.pdf\": {\n" +
                    "        \"Name\": \"0493_s20_qp_21.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1i3q2QcKA_7nc8XOnM2tST3TxpWIxmVYp/view?usp=drivesdk\",\n" +
                    "        \"Size\": 935.951171875\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s22_qp_11.pdf\": {\n" +
                    "        \"Name\": \"0493_s22_qp_11.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1iTMSeb47BGrOV5x2_yKBvaI0NW7icVun/view?usp=drivesdk\",\n" +
                    "        \"Size\": 960\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s22_qp_21.pdf\": {\n" +
                    "        \"Name\": \"0493_s22_qp_21.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1nNbSwQ3ZwX7KRCIAwsX1Cl4vGYInCQLb/view?usp=drivesdk\",\n" +
                    "        \"Size\": 930.2451171875\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s21_qp_11.pdf\": {\n" +
                    "        \"Name\": \"0493_s21_qp_11.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1qTeXNNyvZHN4ehzQhX2MBXfLogio5SAe/view?usp=drivesdk\",\n" +
                    "        \"Size\": 954.1015625\n" +
                    "    },\n" +
                    "    \"may/jun/qp/0493_s23_qp_21.pdf\": {\n" +
                    "        \"Name\": \"0493_s23_qp_21.pdf\",\n" +
                    "        \"Type\": \"Files\",\n" +
                    "        \"URL\": \"https://drive.google.com/file/d/1t-Q83FlHaoCJZ-4XGB0NGxz6-vV1XaCh/view?usp=drivesdk\",\n" +
                    "        \"Size\": 742.064453125\n" +
                    "    }\n" +
                    "}"
        }

        return content.toString()
    }
}