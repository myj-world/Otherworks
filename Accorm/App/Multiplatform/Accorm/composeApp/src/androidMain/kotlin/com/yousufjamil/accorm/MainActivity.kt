package com.yousufjamil.accorm

import App
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.review.testing.FakeReviewManager
import com.google.firebase.messaging.FirebaseMessaging
import com.yousufjamil.accorm.cloudmessaging.CloudMessaging
import database.DatabaseDriverFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import network.getResponse
import screens.poppins
import screens.resources.FileManager
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    private var usageTime = 0L
    private var isReviewed = false
    private var reviewMessageDismissed = false
    private var showReviewRequest = false
    private var reviewAsked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var padding by remember { mutableStateOf(0) }

            LaunchedEffect(
                Unit
            ) {
                DatabaseDriverFactory.initContext(this@MainActivity)
                FileManager.initContext(this@MainActivity)

                checkLatest()

                usageTime = getSharedPreferences("accorm_data", MODE_PRIVATE).getLong(
                    "accorm_usage_time",
                    0L
                )
                isReviewed = getSharedPreferences("accorm_data", MODE_PRIVATE).getBoolean(
                    "accorm_reviewed_play_store",
                    false
                )
                println("Usage Stats: $isReviewed $usageTime $reviewMessageDismissed")
                if (((usageTime / 1000L / 60L).toInt() >= 10) && !reviewMessageDismissed && !isReviewed && !reviewAsked) {
                    showReviewRequest = true
                }
                updateTime()
            }

            suspend fun updatePadding() {
                delay(2000)
                if (padding > 2) padding-- else padding++
                if (!isReviewed && !reviewMessageDismissed) updatePadding()
            }

            LaunchedEffect(Unit) {
                updatePadding()
            }

            App()

            @Composable
            fun requestReview() {
                var show by remember { mutableStateOf(true) }
                var agreedReview by remember { mutableStateOf(false) }
                var displayThanks by remember { mutableStateOf(false) }
                var errorOccurred by remember { mutableStateOf(false) }

                if (show) {
                    AlertDialog(
                        onDismissRequest = {
                            show = false
                        },
                        title = {
                            Text(
                                text = "Rate us",
                                fontSize = 20.sp,
                                fontFamily = poppins,
                                color = Color(0xFF181829)
                            )
                        },
                        text = {
                            Text(
                                text = "You've used the app for over ${(usageTime / 1000L / 60L).toInt()} minutes! Rate us on Google Play Store.",
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFF1f1f36)
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    show = false
                                    agreedReview = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF1f1f36)
                                )
                            ) {
                                Text(
                                    text = "Review now",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFffffff)
                                )
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    show = false
                                    reviewMessageDismissed = true
                                    showReviewRequest = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF1f1f36)
                                )
                            ) {
                                Text(
                                    text = "Not now",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFffffff)
                                )
                            }
                        },
                        backgroundColor = Color.White
                    )
                }

                if (agreedReview) {
                    reviewAsked = true
                    val manager = ReviewManagerFactory.create(this@MainActivity)
                    manager.requestReviewFlow().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            manager.launchReviewFlow(this@MainActivity, task.result).addOnCompleteListener{ it ->
                                if (it.isSuccessful) {
                                    println("Review Success")
                                    displayThanks = true
                                    isReviewed = true
                                    getSharedPreferences("accorm_data", MODE_PRIVATE).edit()
                                        .putBoolean("accorm_reviewed_play_store", true).apply()
                                    agreedReview = false
                                }
                            }
                        } else {
                            @ReviewErrorCode val reviewErrorCode =
                                (task.exception as ReviewException).errorCode
                            println(reviewErrorCode)
                            reviewMessageDismissed = true
                            errorOccurred = true
                        }
                    }
                }

                if (displayThanks) {
                    AlertDialog(
                        onDismissRequest = {
                            displayThanks = false
                        },
                        title = {
                            Text(
                                text = "Rating Success!",
                                fontSize = 20.sp,
                                fontFamily = poppins,
                                color = Color(0xFF181829)
                            )
                        },
                        text = {
                            Text(
                                text = "Thanks for reviewing us on Google Play Store! Your review helps us improve.",
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFF1f1f36)
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    displayThanks = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF1f1f36)
                                )
                            ) {
                                Text(
                                    text = "You're welcome",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFffffff)
                                )
                            }
                        },
                        backgroundColor = Color.White
                    )
                } else if (errorOccurred) {
                    AlertDialog(
                        onDismissRequest = {
                            displayThanks = false
                        },
                        title = {
                            Text(
                                text = "Error Posting Review.",
                                fontSize = 20.sp,
                                fontFamily = poppins,
                                color = Color(0xFF181829)
                            )
                        },
                        text = {
                            Text(
                                text = "Please try again later.",
                                fontSize = 18.sp,
                                fontFamily = poppins,
                                color = Color(0xFF1f1f36)
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    displayThanks = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF1f1f36)
                                )
                            ) {
                                Text(
                                    text = "Ok",
                                    fontSize = 18.sp,
                                    fontFamily = poppins,
                                    color = Color(0xFFffffff)
                                )
                            }
                        },
                        backgroundColor = Color.White
                    )
                }
            }

            Box(Modifier.padding(padding.dp - padding.dp)) {
                if (showReviewRequest) {
                    requestReview()
                }
            }
        }

        askNotificationPermission()
        getToken()
    }

    private suspend fun updateTime() {
        delay(20000)
        usageTime += 20000
        getSharedPreferences("accorm_data", MODE_PRIVATE).edit()
            .putLong("accorm_usage_time", usageTime).apply()

        println("Usage time in minutes: ${(usageTime / 1000L / 60L).toInt()}")

        updateTime()
    }

    private fun getToken() {
        var token: String?
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Error setting up notifications", Toast.LENGTH_SHORT).show()
                return@OnCompleteListener
            }

            token = task.result
            println("FM Token: $token")
        })
    }

    private fun askNotificationPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(
                    this,
                    "Please grant notification & external storage permission via settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
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
                appUpdateManager.startUpdateFlow(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }.addOnFailureListener { e ->
            println("RUN 2 $e")
        }.addOnCanceledListener {
            println("RUN 2 CANCELLED")
        }
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