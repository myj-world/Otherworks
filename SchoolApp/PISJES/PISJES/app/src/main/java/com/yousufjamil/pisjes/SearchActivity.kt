package com.yousufjamil.pisjes

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.yousufjamil.pisjes.R.layout.activity_search)

        var data: List<MutableMap<String, String>> = ArrayList()

        val datum: MutableMap<String, String> = HashMap(2)
        datum["Name"] = "Home"
        datum["URL"] = "https://pisjes.edu.sa"
        data = data.plus(datum)

        datum["Name"] = "Calendar"
        datum["URL"] = "https://pisjes.edu.sa/calendar/"
        data = data.plus(datum)

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf("Name", "URL"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        val listView = findViewById<View>(com.yousufjamil.pisjes.R.id.urlList) as ListView
        listView.adapter = adapter

        val searchView: SearchView = findViewById(com.yousufjamil.pisjes.R.id.searchField)

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                searchView.background = resources.getDrawable(com.yousufjamil.pisjes.R.drawable.search_bg_select)
            } else {
                searchView.background = resources.getDrawable(com.yousufjamil.pisjes.R.drawable.search_bg)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchView.clearFocus()
                    listView.setFilterText(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listView.setFilterText(newText)
                return false
            }
        })

    }
}