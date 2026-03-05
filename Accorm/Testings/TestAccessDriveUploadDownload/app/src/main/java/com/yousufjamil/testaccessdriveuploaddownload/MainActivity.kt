package com.yousufjamil.testaccessdriveuploaddownload

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.yousufjamil.testaccessdriveuploaddownload.ui.theme.TestAccessDriveUploadDownloadTheme
import java.net.URI


class MainActivity : ComponentActivity() {

    var id = ""
    val REQ_CODE_FILE_SELECT = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val id = saveFile("abc.txt", "abc.txt", "text/plain")
        setContent {
            TestAccessDriveUploadDownloadTheme {
                var padding by remember {
                    mutableStateOf(0)
                }
                Column {
                    Handler().postDelayed(
                        {
                            padding = if (padding > 0) padding - 1 else padding + 1
                        }, 1000
                    )
                    Text(
                        text = id,
                        modifier = Modifier.padding(padding.dp - padding.dp)
                    )
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                            type = "application/pdf"
                        }
                        startActivityForResult(intent, REQ_CODE_FILE_SELECT)
                    }) {
                        Text(text = "Select file to upload")
                    }
                }
            }
        }
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                1000
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_FILE_SELECT && resultCode == RESULT_OK) {
            println(data?.data)
            if (data != null && data.data != null) {
                try {
                    id = saveFile(createTmpFileFromUri(this@MainActivity, data.data!!, "abc"))
                } catch (e: Exception) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("File size limit exceeded")
                    builder.setMessage("File size should be less than or equal to 30 MB")
                    builder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun saveFile(file: java.io.File): String {
        var id = "Not yet run"

        println("File Size: "+((file.length()) / (1024*1024)))

        if (((file.length()) / (1024*1024)) <= 30) {
            try {
                val thread = Thread {
                    val fileMetadata = File()
                    fileMetadata.name = file.name + ".pdf"
                    fileMetadata.parents = listOf("1oT1RvqE18TgKTEgYnJ2dDZf3_jpGZ0dJ")
                    val mediaContent = FileContent("application/pdf", file)

                    var credentials =
                        GoogleCredential.fromStream(applicationContext.assets.open("credentials.json"))
                    credentials = credentials.createScoped(
                        listOf(
                            "https://www.googleapis.com/auth/cloud-platform",
                            "https://www.googleapis.com/auth/drive"
                        )
                    )
                    val service = Drive.Builder(
                        NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credentials
                    ).setApplicationName("Accorm-test").build()

                    val fileToUpload =
                        service.files().create(fileMetadata, mediaContent).setFields("id").execute()
                    println("File ID: ${fileToUpload.id}")
                    id = fileToUpload.id

                    file.delete()
                }

                thread.start()
            } catch (e: Exception) {
                id = e.toString()
            }
            return id
        } else {
            throw Exception()
        }
    }
}

fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String): java.io.File {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        val file = java.io.File.createTempFile(fileName, "", context.cacheDir)
        org.apache.commons.io.FileUtils.copyInputStreamToFile(stream,file)
        file
    } catch (e: Exception) {
        e.printStackTrace()
        throw Exception()
    }
}