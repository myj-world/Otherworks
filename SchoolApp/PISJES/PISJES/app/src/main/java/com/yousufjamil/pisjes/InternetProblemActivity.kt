package com.yousufjamil.pisjes

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Suppress("DEPRECATION")
class InternetProblemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_problem)

        val onUrl = intent.getStringExtra(EXTRA_URL)

        val retryBtn: Button = findViewById(R.id.internetProblemRetryBtn)
        retryBtn.setOnClickListener {
            val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected) {
                val mainPageIntent = Intent(this, MainActivity::class.java)
                mainPageIntent.putExtra(EXTRA_URL, onUrl)
                startActivity(mainPageIntent)
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("No connection found. Please check your internet connection.")
                    .setPositiveButton("Ok") {dialog, which ->
                        //Nothing
                    }.show()
            }
        }
    }
}