package com.yousufjamil.pisjes

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = intent.getStringExtra(EXTRA_URL)

        val webView: WebView = findViewById(R.id.websiteWebView)
        val titleWebSite: TextView = findViewById(R.id.pageNameTxt)
        val progressLoad: ProgressBar = findViewById(R.id.loadWebSite)

        webView.loadUrl(url.toString())
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.canGoBack()
        progressLoad.visibility = View.INVISIBLE

        val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> true })
                progressLoad.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> false })
                titleWebSite.text = webView.title
                progressLoad.visibility = View.INVISIBLE
            }
        }
    }

//    fun checkInternet(): Boolean {
//        val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
//        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
//        return isConnected
//    }
}