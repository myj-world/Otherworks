package com.yousufjamil.pisjes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pages = arrayOf("Home", "Calendar", "About", "Why PISJES", "Principal's Message", "Our Team", "Our Campus",
            "Policy Statement", "Junior School Academic Policy", "Middle School Academic Policy",
            "Senior School Academic Policy", "Uniform Policy", "Leave Policy", "Bus Transport Policy",
            "Student Annual Award Rubric", "Documents required for Admission", "Age Criteria for Admission",
            "School Leaving Certificate", "Fee Payment System", "Clearance of School Dues", "Terms and Conditions",
            "Parent Portal")
        println("Length of page is: " + pages.size)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, pages
        )

        val listView = findViewById<ListView>(R.id.urlList)
        listView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.searchField)

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                searchView.background = resources.getDrawable(R.drawable.search_bg_select)
            } else {
                searchView.background = resources.getDrawable(R.drawable.search_bg)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (pages.contains(query)) {
                    adapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            var webpagesearched = ""
            when (adapter.getItem(position)) {
                "Home" -> webpagesearched = "https://pisjes.edu.sa/"
                "Calendar" -> webpagesearched = "https://pisjes.edu.sa/calendar/"
                "About" -> webpagesearched = "https://pisjes.edu.sa/about/"
                "Why PISJES" -> webpagesearched = "https://pisjes.edu.sa/why-pisj-es/"
                "Principal's Message" -> webpagesearched = "https://pisjes.edu.sa/principals-message/"
                "Our Team" -> webpagesearched = "https://pisjes.edu.sa/our-team/"
                "Our Campus" -> webpagesearched = "https://pisjes.edu.sa/our-campus/"
                "Policy Statement" -> webpagesearched = "https://pisjes.edu.sa/policy-statement/"
                "Junior School Academic Policy" -> webpagesearched = "https://pisjes.edu.sa/academic-policy-js/"
                "Middle School Academic Policy" -> webpagesearched = "https://pisjes.edu.sa/academic-policy-ms/"
                "Senior School Academic Policy" -> webpagesearched = "https://pisjes.edu.sa/academic-policy-ss/"
                "Uniform Policy" -> webpagesearched = "https://pisjes.edu.sa/uniform-policy/"
                "Leave Policy" -> webpagesearched = "https://pisjes.edu.sa/leave-policy/"
                "Bus Transport Policy" -> webpagesearched = "https://pisjes.edu.sa/bus-policy/"
                "Student Annual Award Rubric" -> webpagesearched = "https://pisjes.edu.sa/student-annual-awards-rubric/"
                "Documents required for Admission" -> webpagesearched = "https://pisjes.edu.sa/doc-req-admsn/"
                "Age Criteria for Admission" -> webpagesearched = "https://pisjes.edu.sa/age-criteria/"
                "School Leaving Certificate" -> webpagesearched = "https://pisjes.edu.sa/school-leaving-certificate/"
                "Fee Payment System" -> webpagesearched = "https://pisjes.edu.sa/fee-payment-system/"
                "Clearance of School Dues" -> webpagesearched = "https://pisjes.edu.sa/clearance-dues/"
                "Terms and Conditions" -> webpagesearched = "https://pisjes.edu.sa/terms-condition/"
                "Parent Portal" -> webpagesearched = "https://pisjes.edu.sa/parent-portal/"
            }
            val openPageIntent = Intent(this, MainActivity::class.java)
            openPageIntent.putExtra(EXTRA_URL, webpagesearched)
            startActivity(openPageIntent)
            finish()
        }
    }
}