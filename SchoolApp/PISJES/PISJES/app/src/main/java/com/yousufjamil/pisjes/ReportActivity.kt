package com.yousufjamil.pisjes

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val logo: ImageView = findViewById(R.id.reportLogo)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
        rounded.isCircular = true
        logo.setImageDrawable(rounded)

        val email = "muhammadyousufjamil@gmail.com"
        val subject: EditText = findViewById(R.id.etSubject)
        val emaildata: EditText = findViewById(R.id.etEmail)
        val sendbtn: Button = findViewById(R.id.reportBtn)

        sendbtn.setOnClickListener {
            val subjectdata = "PISJES app issue - ${subject.text.toString().trim()}"
            val emaildatatext = emaildata.text.toString().trim()
            sendEmail(email, subjectdata, emaildatatext)
        }

    }

    private fun sendEmail(recepient: String, subject: String, email: String) {
        val mailIntent = Intent(Intent.ACTION_SEND)
        mailIntent.data = Uri.parse("mailto:")
        mailIntent.type = "text/plain"
//        mailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain")
        mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recepient))
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mailIntent.putExtra(Intent.EXTRA_TEXT, email)
        try {
            startActivity(Intent.createChooser(mailIntent, "Choose an email client to continue"))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}