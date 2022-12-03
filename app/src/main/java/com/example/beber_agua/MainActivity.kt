package com.example.beber_agua

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.example.beber_agua.db.AppDatabase
import com.example.beber_agua.db.entity.UserEntity
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonGlassPageRef: ImageView
    private lateinit var waterAmountTextViewRef: TextView
    private lateinit var textViewUserName: TextView
    private lateinit var waterAmountUserProgress: TextView
    private lateinit var buttonListAlarmsRef: Button
    private lateinit var buttonAlarmRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //      hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //        components initial state
        waterAmountTextViewRef = findViewById(R.id.waterAmountTextView)
        waterAmountUserProgress = findViewById(R.id.waterAmountUserProgress)
        textViewUserName = findViewById(R.id.textViewUserName)
        buttonGlassPageRef = findViewById(R.id.imageButtonGlassPage)
        buttonBackRef = findViewById(R.id.imageSettings)
        buttonListAlarmsRef = findViewById(R.id.buttonAlarmList)
        buttonAlarmRegister = findViewById(R.id.buttonAlarmRegister)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()

        fun goToSettingsScreen() {
            var settingScreen = Intent(this, Settings::class.java)
            startActivity(settingScreen)
        }

        fun goToAlarmListScreen() {
            var alarmlistScreen = Intent(this, AlarmList::class.java)
            startActivity(alarmlistScreen)
        }

        fun goToGlassOptionsScreen() {
            var glasslistScreen = Intent(this, GlassList::class.java)
            startActivity(glasslistScreen)
        }

        val userCalled: UserEntity = db.userDao.getById(1)
        var hasUser: Boolean = false

        if (userCalled != null) {
            hasUser = true
            waterAmountTextViewRef.text =
                String.format("%.3f", userCalled.waterAmount) + " ml | Meta"
            val nameUser = userCalled.name.split(' ')[0]
            textViewUserName.text = "Olá, $nameUser"
            waterAmountUserProgress.text =
                if (userCalled.waterAmountDrank == 0F) "0 ml" else String.format(
                    "%.3f",
                    userCalled.waterAmountDrank
                )
        } else {
            goToSettingsScreen()
        }

//      listeners
        buttonGlassPageRef.setOnClickListener {
            goToGlassOptionsScreen()
        }

        buttonAlarmRegister.setOnClickListener {
            val calendar = Calendar.getInstance()
            val exactHour = calendar.get(Calendar.HOUR_OF_DAY)
            val exactMinutes = calendar.get(Calendar.MINUTE) + 5


            var timePickerDialog = TimePickerDialog(this, {
                 timePicker :TimePicker , exactHour : Int, exactMinutes: Int ->
//                logic to create an alarm
                val intentAlarmCreation = Intent(AlarmClock.ACTION_SET_ALARM)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_HOUR, exactHour)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_MINUTES, exactMinutes)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_MESSAGE, R.string.text_alarm_description )
                startActivity(intentAlarmCreation)
            }, exactHour, exactMinutes, true)
            timePickerDialog.show()
        }


        buttonBackRef.setOnClickListener {
            goToSettingsScreen()
        }

        buttonListAlarmsRef.setOnClickListener {
            goToAlarmListScreen()
        }
    }
}