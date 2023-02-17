package com.yousufjamil.pisjes

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION", "SetJavascriptEnabled", "ClickableViewAccessibility")
class MainActivity : AppCompatActivity() {

    var urlParcelable: String? = ""
    lateinit var webView: WebView
    lateinit var toggle: ActionBarDrawerToggle

    private val STORAGE_PERMISSION_CODE: Int = 1000

    var downloadurl = ""
    var downloaduseragent = ""
    var downloadcontentDisposition = ""
    var downloadmimetype = ""

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

//        val darkswitch: Switch = navView.getHeaderView(0).findViewById(R.id.darkswitch)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            val logonav = navView.getHeaderView(0).findViewById<ImageView>(R.id.logonav)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logotwo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.isCircular = true
            logonav.setImageDrawable(rounded)
//            darkswitch.isChecked = false
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
//            darkswitch.isChecked = true
        }

//        darkswitch.setOnCheckedChangeListener { _, isChecked ->
//            if (darkswitch.isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//        }

        val errortxt: TextView = findViewById(R.id.errortxt)

        webView.loadUrl(urlParcelable.toString())
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.canGoBack()
        progressLoad.visibility = View.INVISIBLE
        errortxt.visibility = View.INVISIBLE

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                urlParcelable = url
                checkInternet()
                super.onPageStarted(view, url, favicon)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> true })
                progressLoad.visibility = View.VISIBLE
                errortxt.visibility = View.INVISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.setOnTouchListener(View.OnTouchListener { v, event -> false })

                progressLoad.visibility = View.INVISIBLE
                println("Current URL: ${webView.url}")
                urlParcelable = webView.url
                println("Current URL: $urlParcelable")
                if (webView.url == "about:blank") {
                    errortxt.visibility = View.VISIBLE
                }
            }
        }

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            downloadurl = url
            downloaduseragent = userAgent
            downloadcontentDisposition = contentDisposition
            downloadmimetype = mimetype
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadDialog(url, userAgent, contentDisposition, mimetype)
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                }
            } else {
                downloadDialog(url, userAgent, contentDisposition, mimetype)
            }
        }
    }

    fun downloadDialog(url:String,userAgent:String,contentDisposition:String,mimetype:String) {
        val filename = URLUtil.guessFileName(url, contentDisposition, mimetype)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Download")
        builder.setMessage("Do you want to save $filename")
        builder.setPositiveButton("Yes") { dialog, which ->
            val request = DownloadManager.Request(Uri.parse(url))
            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("Cookie",cookie)
            request.addRequestHeader("User-Agent",userAgent)
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val downloadmanager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename)
            downloadmanager.enqueue(request)
        }
        builder.setNegativeButton("Cancel")
        {dialog, which ->
            dialog.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadDialog(downloadurl, downloaduseragent, downloadcontentDisposition, downloadmimetype)
                } else {
                    Toast.makeText(this, "Permission denied! Please allow app permission through settings to download file", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.info) {
            val infoIntent = Intent(this, InfoActivity::class.java)
            startActivity(infoIntent)
//            finish()
        } else if (item.itemId == R.id.report) {
            val reportIntent = Intent(this, ReportActivity::class.java)
            startActivity(reportIntent)
//            finish()
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