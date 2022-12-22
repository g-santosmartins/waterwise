package com.example.beber_agua

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.beber_agua.db.AppDatabase
import com.example.beber_agua.db.entity.UserEntity
import com.example.beber_agua.utils.CalculateImc
import com.example.beber_agua.utils.CalculateMetabolicDailyNeed
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonGlassPageRef: ImageView
    private lateinit var waterAmountTextViewRef: TextView
    private lateinit var textViewUserName: TextView
    private lateinit var waterAmountUserProgress: TextView
    private lateinit var textViewProgressMesssage: TextView
    private lateinit var buttonListAlarmsRef: Button
    private lateinit var buttonAlarmRegister: Button

    private fun instanceAllFields() {
        waterAmountTextViewRef = findViewById(R.id.waterAmountTextView)
        waterAmountUserProgress = findViewById(R.id.waterAmountUserProgress)
        textViewUserName = findViewById(R.id.textViewUserName)
        buttonGlassPageRef = findViewById(R.id.imageButtonGlassPage)
        buttonBackRef = findViewById(R.id.imageSettings)
        buttonListAlarmsRef = findViewById(R.id.buttonAlarmList)
        buttonAlarmRegister = findViewById(R.id.buttonAlarmRegister)
        textViewProgressMesssage = findViewById(R.id.textViewProgressMesssage)
    }

    private fun instanceDatabase(): AppDatabase {
        return Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()
    }

    private fun goToSettingsScreen() {
        var settingScreen = Intent(this, Settings::class.java)
        startActivity(settingScreen)
    }

    private fun goToAlarmListScreen() {
        var alarmlistScreen = Intent(this, AlarmList::class.java)
        startActivity(alarmlistScreen)
    }

    private fun goToGlassOptionsScreen() {
        var glasslistScreen = Intent(this, GlassList::class.java)
        startActivity(glasslistScreen)
    }

    private fun checkResetWaterGoal() {
        val db = instanceDatabase()

        val userCalled: UserEntity = db.userDao.getById(1)

        val calendar = Calendar.getInstance()
        val currentYearDay = calendar.get(Calendar.DAY_OF_YEAR)

        if (userCalled != null && userCalled.dayOfYear != currentYearDay) {
            db.userDao.update(
                UserEntity(
                    id = 1,
                    name = userCalled.name,
                    email = "exemplo@exemplo.com.br",
                    weight = userCalled.weight,
                    age = userCalled.age,
                    gender = userCalled.gender,
                    height = userCalled.height,
                    waterAmount = userCalled.waterAmount,
                    waterAmountDrank = 0F,
                    caloriesDailyAmount = userCalled.caloriesDailyAmount,
                    dayOfYear = currentYearDay,
                    dailyGoalCompleted = userCalled.dailyGoalCompleted

                )
            )
        }
    }

    private fun searchForUserData() {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()

        val userCalled: UserEntity = db.userDao.getById(1)

        if (userCalled != null) {
            waterAmountTextViewRef.text =
                String.format("%.3f", userCalled.waterAmount) + " ml | Meta"
            val nameUser = userCalled.name.split(' ')[0]
            textViewUserName.text = "Olá, $nameUser"
            waterAmountUserProgress.text =
                if (userCalled.waterAmountDrank == 0F) "0 ml" else String.format(
                    "%.3f" + "ml",
                    userCalled.waterAmountDrank
                )
            if (userCalled.waterAmountDrank >= userCalled.waterAmount) {
                val massage = "Parabéns $nameUser, dia finalizado! \uD83C\uDF89"
                textViewProgressMesssage.text = "Boa $nameUser, dia finalizado! \uD83C\uDF89"
                AlertDialog.Builder(this)
                    .setTitle(massage)
                    .setMessage(R.string.text_contratulations_description)
                    .show()
            }

        } else {
            goToSettingsScreen()
        }
    }
//    notification channel
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(1.toString(), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        calls notification channel instance
        createNotificationChannel()

        var builder = NotificationCompat.Builder(this, 1.toString())
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentTitle("Fala humano! Tudo em cima? \uD83D\uDC99")
            .setContentText("Bora se hidratar? Abra o app e veja quanto falta para atingir a sua meta  \uD83D\uDC99")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(2, builder.build())
        }


        //      hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //        components initial state
        instanceAllFields()

        searchForUserData()

//      listeners
        buttonGlassPageRef.setOnClickListener {
            goToGlassOptionsScreen()
        }

        buttonAlarmRegister.setOnClickListener {
            val calendar = Calendar.getInstance()
            val exactHour = calendar.get(Calendar.HOUR_OF_DAY)
            val exactMinutes = calendar.get(Calendar.MINUTE) + 5


            var timePickerDialog =
                TimePickerDialog(this, { _: TimePicker, exactHour: Int, exactMinutes: Int ->
//                logic to create an alarm
                    val intentAlarmCreation = Intent(AlarmClock.ACTION_SET_ALARM)
                    intentAlarmCreation.putExtra(AlarmClock.EXTRA_HOUR, exactHour)
                    intentAlarmCreation.putExtra(AlarmClock.EXTRA_MINUTES, exactMinutes)
                    intentAlarmCreation.putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        R.string.text_alarm_description
                    )
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
    override fun onStart() {
        super.onStart()
        checkResetWaterGoal()
        searchForUserData()
    }
}
