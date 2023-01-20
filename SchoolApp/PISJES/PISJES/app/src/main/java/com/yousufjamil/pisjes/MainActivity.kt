package com.yousufjamil.pisjes

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val url = intent.getStringExtra(EXTRA_URL)

        val webView: WebView = findViewById(R.id.websiteWebView)
//        val titleWebSite: TextView = findViewById(R.id.pageNameTxt)
        val progressLoad: ProgressBar = findViewById(R.id.loadWebSite)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> webView.loadUrl("https://pisjes.edu.sa")
                R.id.calendar -> webView.loadUrl("https://pisjes.edu.sa/calendar/")
                R.id.junior -> webView.loadUrl("https://pisjes.edu.sa/pg-nursery-reception/")
                R.id.middle -> webView.loadUrl("https://pisjes.edu.sa/middle-school/")
                R.id.senior -> webView.loadUrl("https://pisjes.edu.sa/senior-school/")
                R.id.parentportal -> webView.loadUrl("https://pisjes.edu.sa/parent-portal/")
                else -> Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawers()
            true
        }




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
//                titleWebSite.text = webView.title
//                supportActionBar?.title = webView.title
                progressLoad.visibility = View.INVISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    fun checkInternet(): Boolean {
//        val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
//        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
//        return isConnected
//    }
}