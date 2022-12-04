package com.example.beber_agua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.example.beber_agua.db.AppDatabase
import com.example.beber_agua.db.entity.UserEntity

class GlassList : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonAdd500: Button
    private lateinit var buttonAdd400: Button
    private lateinit var buttonAdd300: Button
    private lateinit var buttonAdd200: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glass_list)
        fun goToHomePage() {
            var homeScreen = Intent(this, MainActivity::class.java)
            startActivity(homeScreen)
        }

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()


        val userCalled: UserEntity = db.userDao.getById(1)
        var hasUser: Boolean = false

        if (userCalled != null) {
            hasUser = true
            if (userCalled.waterAmountDrank == 0F) "0 ml" else String.format(
                "%.3f",
                userCalled.waterAmountDrank
            )
        } else {
            goToHomePage()
        }
//        hides status bar on app load
        supportActionBar!!.hide()
//      Removes the dark mode from the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        buttonBackRef = findViewById(R.id.imageViewBackIcon)
        buttonAdd500 = findViewById(R.id.buttonAdd500)
        buttonAdd400 = findViewById(R.id.buttonAdd400)
        buttonAdd300 = findViewById(R.id.buttonAdd300)
        buttonAdd200 = findViewById(R.id.buttonAdd200)

        buttonBackRef.setOnClickListener {
            goToHomePage()
        }
//        button glass actions
        buttonAdd500.setOnClickListener {
            if (userCalled != null) {
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = userCalled.name,
                        email = "exemplo@exemplo.com.br",
                        weight = userCalled.weight,
                        age = userCalled.age,
                        waterAmount = userCalled.waterAmount,
                        waterAmountDrank = userCalled.waterAmountDrank + 0.5F
                    )
                )
                goToHomePage()
            } else {
                goToHomePage()
            }
        }
        buttonAdd400.setOnClickListener {
            if (userCalled != null) {
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = userCalled.name,
                        email = "exemplo@exemplo.com.br",
                        weight = userCalled.weight,
                        age = userCalled.age,
                        waterAmount = userCalled.waterAmount,
                        waterAmountDrank = userCalled.waterAmountDrank + 0.4F
                    )
                )
                goToHomePage()
            } else {
                goToHomePage()
            }
        }
        buttonAdd300.setOnClickListener {
            if (userCalled != null) {
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = userCalled.name,
                        email = "exemplo@exemplo.com.br",
                        weight = userCalled.weight,
                        age = userCalled.age,
                        waterAmount = userCalled.waterAmount,
                        waterAmountDrank = userCalled.waterAmountDrank + 0.3F
                    )
                )
                goToHomePage()
            } else {
                goToHomePage()
            }
        }
        buttonAdd200.setOnClickListener {
            if (userCalled != null) {
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = userCalled.name,
                        email = "exemplo@exemplo.com.br",
                        weight = userCalled.weight,
                        age = userCalled.age,
                        waterAmount = userCalled.waterAmount,
                        waterAmountDrank = userCalled.waterAmountDrank + 0.2F
                    )
                )
                goToHomePage()
            } else {
                goToHomePage()
            }

        }

    }
}