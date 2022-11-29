package com.example.beber_agua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonListAlarmsRef: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        fun goToSettingsScreen() {
            var settingScreen = Intent(this, Settings::class.java)
            startActivity(settingScreen)
        }
        fun goToAlarmListScreen() {
            var alarmlistScreen = Intent(this, AlarmList::class.java)
            startActivity(alarmlistScreen)
        }

        buttonBackRef= findViewById(R.id.imageSettings)

        buttonBackRef.setOnClickListener{
            goToSettingsScreen()
        }
         buttonListAlarmsRef = findViewById(R.id.buttonAlarmList)

        buttonListAlarmsRef.setOnClickListener{
            goToAlarmListScreen()
        }


    }
}