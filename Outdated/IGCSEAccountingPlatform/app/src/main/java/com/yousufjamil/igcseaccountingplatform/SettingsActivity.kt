package com.yousufjamil.igcseaccountingplatform

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.text.HtmlCompat
import com.yousufjamil.igcseaccountingplatform.theme.ChangeTheme


class SettingsActivity : ChangeTheme(), AdapterView.OnItemSelectedListener {

    var selecteditem: Any? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
//        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
//        var userTheme = sharedPref.getString(R.string.preference_file_key.toString(), "Theme.Default")
//        val themeId = resources.getIdentifier(userTheme, "style", packageName)

        val sharedGradients = arrayOf(getSharedPreferences("themeInfo", MODE_PRIVATE))
        val userTheme = sharedGradients[0].getString("userTheme", "Theme.Default")
        val themeId = resources.getIdentifier(userTheme.toString(), "style", packageName)
        setTheme(themeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var position = 0

        when (userTheme) {
            "Theme.Default" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8787c6")))
                position = 0
            }
            "Theme.Red" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#d56464")))
                position = 1
            }
            "Theme.Green" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00c98e")))
                position = 2
            }
            "Theme.Blue" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#008ec9")))
                position = 3
            }
            "Theme.Teal" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#009696")))
                position = 4
            }
        }

//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8787c6")))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            supportActionBar?.title = Html.fromHtml("<font color='#FFFFFF'>Accorm</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            supportActionBar?.title = Html.fromHtml("<font color='#FFFFFF'>Accorm</font>")
        }

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

        themepickerdrp.setSelection(position)
        themepickerdrp.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selecteditem = parent?.getItemAtPosition(position)
        println("Theme: $selecteditem")

        changeTheme(selecteditem)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
//        Do nothing
//        selecteditem = "Default"
//        println("Theme: $selecteditem")
    }
}