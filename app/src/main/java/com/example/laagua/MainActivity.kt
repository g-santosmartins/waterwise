package com.example.laagua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.example.beber_agua.R
import com.example.laagua.db.AppDatabase
import com.example.laagua.db.entity.UserEntity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonCloseRef: ImageView
    private lateinit var waterAmountTextViewRef: TextView
    private lateinit var buttonListAlarmsRef: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        waterAmountTextViewRef = findViewById(R.id.waterAmountTextView)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()

        val userCalled : UserEntity = db.userDao.getById(1)
        var hasUser : Boolean = false

        if(userCalled != null) {
            hasUser = true
            waterAmountTextViewRef.text = String.format("%.3f", userCalled.waterAmount) + " L/dia"

        }


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

        fun endProgramProcess() {
            finishAndRemoveTask()
            exitProcess(0);
        }

//      components initial state
        buttonCloseRef = findViewById(R.id.imageViewCloseButton)
        buttonBackRef= findViewById(R.id.imageSettings)
        buttonListAlarmsRef = findViewById(R.id.buttonAlarmList)

//      listeners
        buttonCloseRef.setOnClickListener{
            println("Adicionando copo de agua")
        }

        buttonBackRef.setOnClickListener{
            goToSettingsScreen()
        }

        buttonListAlarmsRef.setOnClickListener{
            goToAlarmListScreen()
        }
    }
}