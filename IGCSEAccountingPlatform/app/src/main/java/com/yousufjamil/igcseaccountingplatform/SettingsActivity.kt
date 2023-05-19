package com.yousufjamil.igcseaccountingplatform

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var selecteditem: Any? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8787c6")))
        supportActionBar?.title = Html.fromHtml("<font color='#FFFFFF'>Settings</font>")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val themepickerdrp: Spinner = findViewById(R.id.themepickerdrp)
        ArrayAdapter.createFromResource(
            this,
            R.array.themesarray,
            android.R.layout.simple_spinner_item
        ).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            themepickerdrp.adapter = adapter
        }

        themepickerdrp.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selecteditem = parent?.getItemAtPosition(position)
        println("Theme: $selecteditem")
        if (selecteditem == "Red") {
            setTheme(R.style.Theme_Red)
//            recreate()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selecteditem = "Default"
        println("Theme: $selecteditem")
    }
}