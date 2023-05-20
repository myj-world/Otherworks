package com.yousufjamil.igcseaccountingplatform

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class ChangeTheme: AppCompatActivity() {
//    var themes = listOf("Theme.Default", "Theme.Red", "Theme.Green", "Theme.Blue", "Theme.Teal")
//    var alreadyRecreated = false
//    open fun setTheme(theme: String) {
//        val themeId = resources.getIdentifier(theme, "style", packageName)
//        setTheme(themeId)
//    }
    open fun changeTheme(userThemePref: Any?) {
        when (userThemePref) {
            "Default" -> {
                setTheme(R.style.Theme_Default)
            }

            "Red" -> {
                setTheme(R.style.Theme_Red)
            }

            "Green" -> {
                setTheme(R.style.Theme_Green)
            }

            "Blue" -> {
                setTheme(R.style.Theme_Blue)
            }

            "Teal" -> {
                setTheme(R.style.Theme_Teal)
            }
            else -> setTheme(R.style.Theme_Default)
        }

//        val sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
//        with(sharedPreferences.edit()) {
//            putString(getString(R.string.preference_file_key), "Theme."+userThemePref.toString())
//        }
        val sharedGradients = arrayOf(getSharedPreferences("themeInfo", MODE_PRIVATE))
        val editor = arrayOf(sharedGradients[0].edit())

        editor[0] = sharedGradients[0].edit()
        editor[0].putString("userTheme", "Theme.$userThemePref")
        editor[0].apply()

        Toast.makeText(this, "Click the back button to apply the theme", Toast.LENGTH_SHORT).show()

//        if (alreadyRecreated) {
//            recreate()
//            alreadyRecreated = true
//        }
    }
}