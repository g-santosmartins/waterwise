package com.example.beber_agua

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.ImageView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatDelegate
import java.util.*


class AlarmList : AppCompatActivity() {

    private lateinit var buttonBackRef : ImageView
    private lateinit var buttonAddAlarmRef : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)

//      hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        fun goToHomePage() {
            var homeScreen = Intent(this, MainActivity::class.java)
            startActivity(homeScreen)
        }

        buttonBackRef  = findViewById(R.id.imageViewBackIcon)
        buttonAddAlarmRef  = findViewById(R.id.floatingButtonAddAlarm)

        buttonBackRef.setOnClickListener{
            goToHomePage()
        }

        buttonAddAlarmRef.setOnClickListener {
            val calendar = Calendar.getInstance()
            val exactHour = calendar.get(Calendar.HOUR_OF_DAY)
            val exactMinutes = calendar.get(Calendar.MINUTE) + 5


            var timePickerDialog = TimePickerDialog(this, {
                    timePicker : TimePicker, exactHour : Int, exactMinutes: Int ->
//                logic to create an alarm
                val intentAlarmCreation = Intent(AlarmClock.ACTION_SET_ALARM)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_HOUR, exactHour)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_MINUTES, exactMinutes)
                intentAlarmCreation.putExtra(AlarmClock.EXTRA_MESSAGE, R.string.text_alarm_description )
                startActivity(intentAlarmCreation)
            }, exactHour, exactMinutes, true)
            timePickerDialog.show()
        }
    }
}