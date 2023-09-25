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

        val pages = arrayOf(
            "Home",
            "Calendar",
            "About",
            "Why PISJES",
            "Principal's Message",
            "Our Team",
            "Our Campus",
            "Policy Statement",
            "Junior School",
            "Middle School",
            "Senior School",
            "Documents required for Admission",
            "Age Criteria for Admission",
            "School Leaving Certificate",
            "Fee Structure",
            "Fee Payment System",
            "Clearance of School Dues",
            "Terms and Conditions",
            "School Timings",
            "School Books",
            "Head & Vice-head prefects",
            "Clubs",
            "House System",
            "MUN",
            "Student Achievements",
            "Career Counselling",
            "FAQs Undergraduate Programmes Pakistani Universities",
            "Alumni Diaries",
            "Alumni Register",
            "Alumni",
            "Parent Engagement Platform - PEP",
            "Parent Principal Conference - PPC",
            "Parent Portal",
            "Contact",
            "Submit your query",
            "School Management Council",
            "FAQs",
            "Teacher's Training Program",
            "Careers",
            "Apply Online",
            "Gallery",
            "Press Release"
        )
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
                "PISJES Policies" -> webpagesearched = "https://pisjes.edu.sa/pisj-es-policies/"
                "Junior School" -> webpagesearched = "https://pisjes.edu.sa/pg-nursery-reception/"
                "Middle School" -> webpagesearched = "https://pisjes.edu.sa/middle-school/"
                "Senior School" -> webpagesearched = "https://pisjes.edu.sa/senior-school/"
                "Documents required for Admission" -> webpagesearched = "https://pisjes.edu.sa/doc-req-admsn/"
                "Age Criteria for Admission" -> webpagesearched = "https://pisjes.edu.sa/age-criteria/"
                "School Leaving Certificate" -> webpagesearched = "https://pisjes.edu.sa/school-leaving-certificate/"
                "Fee Structure" -> webpagesearched = "https://pisjes.edu.sa/fee-structure/"
                "Fee Payment System" -> webpagesearched = "https://pisjes.edu.sa/fee-payment-system/"
                "Clearance of School Dues" -> webpagesearched = "https://pisjes.edu.sa/clearance-dues/"
                "Terms and Conditions" -> webpagesearched = "https://pisjes.edu.sa/terms-condition/"
                "School Timings" -> webpagesearched = "https://pisjes.edu.sa/school-timings/"
                "School Books" -> webpagesearched = "https://pisjes.edu.sa/school-books/"
                "Head & Vice-head prefects" -> webpagesearched = "https://pisjes.edu.sa/head-and-vice-head-prefects/"
                "Clubs" -> webpagesearched = "https://pisjes.edu.sa/clubs-societies/"
                "House System" -> webpagesearched = "https://pisjes.edu.sa/house-system/"
                "MUN" -> webpagesearched = "https://pisjes.edu.sa/mun-pisj/"
                "Student Achievements" -> webpagesearched = "https://pisjes.edu.sa/std-achv/"
                "Career Counselling" -> webpagesearched = "https://pisjes.edu.sa/career-counselling/"
                "FAQs Undergraduate Programmes Pakistani Universities" -> webpagesearched = "https://pisjes.edu.sa/faqs/"
                "Alumni Diaries" -> webpagesearched = "https://pisjes.edu.sa/alumni-diaries/"
                "Alumni Register" -> webpagesearched = "https://pisjes.edu.sa/alumni-register/"
                "Alumni" -> webpagesearched = "https://pisjes.edu.sa/alumni/"
                "Parent Engagement Platform - PEP" -> webpagesearched = "https://pisjes.edu.sa/pisj-pep/"
                "Parent Principal Conference - PPC" -> webpagesearched = "https://pisjes.edu.sa/parent-principal-conference-page/"
                "Parent Portal" -> webpagesearched = "https://pisjes.edu.sa/parent-portal/"
                "Contact" -> webpagesearched = "https://pisjes.edu.sa/contact/"
                "Submit your query" -> webpagesearched = "https://pisjes.edu.sa/submit-your-query/"
                "School Management Council" -> webpagesearched = "https://pisjes.edu.sa/smc/"
                "FAQs" -> webpagesearched = "https://pisjes.edu.sa/faqs-teams-portal/"
                "Teacher's Training Program" -> webpagesearched = "https://pisjes.edu.sa/teachers-training-program/"
                "Careers" -> webpagesearched = "https://pisjes.edu.sa/careers/"
                "Apply Online" -> webpagesearched = "https://pisjes.edu.sa/apply-online/"
                "Gallery" -> webpagesearched = "https://pisjes.edu.sa/gallery/"
                "Press Release" -> webpagesearched = "https://pisjes.edu.sa/press/"
            }
            val openPageIntent = Intent(this, MainActivity::class.java)
            openPageIntent.putExtra(EXTRA_URL, webpagesearched)
            startActivity(openPageIntent)
            finish()
        }
    }
}