package com.yousufjamil.pisjes

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION", "SetJavascriptEnabled", "ClickableViewAccessibility")
class MainActivity : AppCompatActivity() {

    var urlParcelable: String? = ""
    lateinit var webView: WebView
    lateinit var toggle: ActionBarDrawerToggle

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("Current URL saved: $urlParcelable")
        outState.putString(EXTRA_URL, urlParcelable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            urlParcelable = savedInstanceState.getString(EXTRA_URL, "https://pisjes.edu.sa")
            println("Current URL retrieved: $urlParcelable")
        }

        println("Current URL retrieved: $urlParcelable")

        supportActionBar?.title = "PISJES"

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        if (urlParcelable == "") {
            println("Current URL is $urlParcelable so reassign")
            urlParcelable = intent.getStringExtra(EXTRA_URL)
        }

        webView = findViewById(R.id.websiteWebView)
//        val titleWebSite: TextView = findViewById(R.id.pageNameTxt)
        val progressLoad: ProgressBar = findViewById(R.id.loadWebSite)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> urlParcelable = "https://pisjes.edu.sa"
                R.id.calendar -> urlParcelable = "https://pisjes.edu.sa/calendar/"
                R.id.junior -> urlParcelable = "https://pisjes.edu.sa/pg-nursery-reception/"
                R.id.middle -> urlParcelable = "https://pisjes.edu.sa/middle-school/"
                R.id.senior -> urlParcelable = "https://pisjes.edu.sa/senior-school/"
                R.id.parentportal -> urlParcelable = "https://pisjes.edu.sa/parent-portal/"
                else -> Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
            }
            webView.loadUrl(urlParcelable.toString())
            drawerLayout.closeDrawers()
            true
        }

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            val logonav = navView.getHeaderView(0).findViewById<ImageView>(R.id.logonav)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logotwo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.isCircular = true
            logonav.setImageDrawable(rounded)
        } else {
            val navheader = navView.getHeaderView(0)
            val name = navheader.findViewById<TextView>(R.id.name)
            val urlnav = navheader.findViewById<TextView>(R.id.url)
            val logonav = navView.getHeaderView(0).findViewById<ImageView>(R.id.logonav)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.isCircular = true
            logonav.setImageDrawable(rounded)
            val navnewbg = ColorDrawable(Color.parseColor("#068a45"))
            val navtextcolour = Color.parseColor("#FFFFFF")
            navheader.background = navnewbg
            name.setTextColor(navtextcolour)
            urlnav.setTextColor(navtextcolour)
        }

        webView.loadUrl(urlParcelable.toString())
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.canGoBack()
        progressLoad.visibility = View.INVISIBLE

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                urlParcelable = url
                checkInternet()
                super.onPageStarted(view, url, favicon)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> true })
                progressLoad.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> false })

                progressLoad.visibility = View.INVISIBLE
                println("Current URL: ${webView.url}")
                urlParcelable = webView.url
                println("Current URL: $urlParcelable")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.info) {
            val infoIntent = Intent(this, InfoActivity::class.java)
            startActivity(infoIntent)
        } else if (item.itemId == R.id.report) {
            val reportIntent = Intent(this, ReportActivity::class.java)
            startActivity(reportIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return true
    }

    fun checkInternet() {
        val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected) {
            val internetIssueIntent = Intent(this, InternetProblemActivity::class.java)
            internetIssueIntent.putExtra(EXTRA_URL, urlParcelable)
            startActivity(internetIssueIntent)
            finish()
        }
    }
}