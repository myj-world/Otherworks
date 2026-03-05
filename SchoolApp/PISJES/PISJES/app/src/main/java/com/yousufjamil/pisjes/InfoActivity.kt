package com.yousufjamil.pisjes

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val logo: ImageView = findViewById(R.id.infoLogo)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
        rounded.isCircular = true
        logo.setImageDrawable(rounded)

    }
}