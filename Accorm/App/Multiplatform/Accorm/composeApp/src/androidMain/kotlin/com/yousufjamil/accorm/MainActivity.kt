package com.yousufjamil.accorm

import App
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import network.getResponse
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect (
                Unit
            ) {
                checkLatest()
            }

            App()
        }
    }


    private fun checkLatest() {
        println("RUN 1")

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                println("RUN 2")
//                InstallStateUpdatedListener { installState ->
//                    if (installState.installStatus() == InstallStatus.DOWNLOADED) {
//                        appUpdateManager.completeUpdate()
//                    }
//                }

//                appUpdateManager.startUpdateFlowForResult(
//                    appUpdateInfo,
//                    AppUpdateType.IMMEDIATE,
//                    this,
//                    100
//                )

                appUpdateManager.startUpdateFlow(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            } else {
                println("RUN 3 ${appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE} ${appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)}")
            }
        }.addOnFailureListener { e->
            println("RUN 2 $e")
        }.addOnCanceledListener {
            println("RUN 2 CANCELLED")
        }

//        val version = this.packageManager.getPackageInfo(this.packageName, 0).versionName
//        var latestAppVersion = ""
//
//        coroutineScope.launch {
//            try {
//                val response =
//                    getResponse("https://api.github.com/repos/myj-world/Otherworks/releases/latest")
//                if (response != null) {
//                    val json = Json { ignoreUnknownKeys = true }
//                    val release = json.decodeFromString<release>(response)
//                    latestAppVersion = release.tag_name
//                }
//            } catch (_: Exception) {}
//        }
//
//        return version == latestAppVersion
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Serializable
data class release(
    val tag_name: String
)