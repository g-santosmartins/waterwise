package com.example.beber_agua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.beber_agua.db.AppDatabase
import com.example.beber_agua.db.entity.UserEntity

class AmountWater : AppCompatActivity() {

    private lateinit var inputGlassAdd : EditText
    private lateinit var buttonAddAmountWater : Button
    private lateinit var buttonSaveGlassConfig : Button
    private lateinit var buttonBackRef: ImageView


    private fun goToHomePage() {
        var homeScreen = Intent(this, MainActivity::class.java)
        startActivity(homeScreen)
    }

    private fun goToGlassListPage() {
        var glasslistScreen = Intent(this, GlassList::class.java)
        startActivity(glasslistScreen)
    }

    private fun searchForUserData(): UserEntity {
        val databaseInstance =  instanceDatabase()
        val userCalled: UserEntity = databaseInstance.userDao.getById(1)

        if (userCalled != null) {
            if (userCalled.waterAmountDrank == 0F) "0 ml" else String.format(
                "%.3f",
                userCalled.waterAmountDrank
            )
        } else {
            goToHomePage()
        }

        return userCalled

    }

    private fun instanceDatabase(): AppDatabase {
        return Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()
    }

    private fun validatInputs(): Boolean {
        if (inputGlassAdd.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_error_glass, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_water)
        supportActionBar!!.hide()
        val db = instanceDatabase()

        val userCalled: UserEntity = db.userDao.getById(1)

        inputGlassAdd = findViewById(R.id.inputGlassAdd)
        buttonAddAmountWater = findViewById(R.id.buttonAddAmountWater)
        buttonSaveGlassConfig = findViewById(R.id.buttonSaveGlassConfig)
        buttonBackRef = findViewById(R.id.imageViewBackIcon)



        buttonAddAmountWater.setOnClickListener {
            println("entrando +")
        }

        buttonSaveGlassConfig.setOnClickListener {
            println("salvando copo")
        }

        buttonBackRef.setOnClickListener {
            goToGlassListPage()
        }

    }
}