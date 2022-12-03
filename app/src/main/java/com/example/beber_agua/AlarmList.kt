package com.example.beber_agua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate


class AlarmList : AppCompatActivity() {

    private lateinit var buttonBackRef : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)

//      hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        fun goToSettingsPage() {
            var homeScreen = Intent(this, MainActivity::class.java)
            startActivity(homeScreen)
        }

        buttonBackRef  = findViewById(R.id.imageViewBackIcon)

        buttonBackRef.setOnClickListener{
            goToSettingsPage()
        }
    }
}