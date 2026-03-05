package com.yousufjamil.pisjes

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            val logosplash = findViewById<ImageView>(R.id.splashSchoolLogo)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logotwo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.isCircular = true
            logosplash.setImageDrawable(rounded)
        } else {
            val layout: ConstraintLayout = findViewById(R.id.ConstraintLayoutSplash)
            val name = findViewById<TextView>(R.id.splashLogoText)
            val splashlogo = findViewById<ImageView>(R.id.splashSchoolLogo)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.isCircular = true
            splashlogo.setImageDrawable(rounded)
            val newbg = ColorDrawable(Color.parseColor("#000000"))
            val textcolour = Color.parseColor("#FFFFFF")
            layout.background = newbg
            name.setTextColor(textcolour)
        }

        val splashLaunchAppName: LinearLayout = findViewById(R.id.splashLogoHolder)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide_transition)
        splashLaunchAppName.startAnimation(slideAnimation)

        Handler().postDelayed(
            {
                val reverseSlideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide_reverse_transition)
                splashLaunchAppName.startAnimation(reverseSlideAnimation)
            }, 3000
        )

        Handler().postDelayed({
            val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected) {
                val finishLoadingIntent = Intent(this, MainActivity::class.java)
                finishLoadingIntent.putExtra(EXTRA_URL, "https://pisjes.edu.sa")
                startActivity(finishLoadingIntent)
                finish()
            } else {
                val internetIssueIntent = Intent(this, InternetProblemActivity::class.java)
                internetIssueIntent.putExtra(EXTRA_URL, "https://pisjes.edu.sa")
                startActivity(internetIssueIntent)
                finish()
            }
        }, 4300)

    }
}