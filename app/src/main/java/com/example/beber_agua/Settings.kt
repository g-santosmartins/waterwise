package com.example.beber_agua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.room.Room
import com.example.beber_agua.db.AppDatabase
import com.example.beber_agua.db.entity.UserEntity
import com.example.beber_agua.utils.CalculateHowMuchWater

class Settings : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonDeletion: ImageView
    private lateinit var buttonCalculateHowMuchWaterRef: AppCompatButton
    private lateinit var inputNameRef: EditText
    private lateinit var inputWeightRef: EditText
    private lateinit var inputAgeRef: EditText
    private lateinit var textWaterGoalRef: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.hide()

        buttonBackRef = findViewById(R.id.imageViewBackIcon)
        buttonCalculateHowMuchWaterRef = findViewById(R.id.buttonCalculateWaterAmount)
        buttonDeletion = findViewById(R.id.imageViewDeleteIcon)
        inputNameRef = findViewById(R.id.inputName)
        inputWeightRef = findViewById(R.id.inputWeight)
        inputAgeRef = findViewById(R.id.inputAge)
        textWaterGoalRef = findViewById(R.id.textWaterGoal)
//        catching a db instance
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()

        val userCalled: UserEntity = db.userDao.getById(1)
        var hasUser: Boolean = false
        if (userCalled != null) {
            hasUser = true
            inputNameRef.setText(userCalled.name.toString())
            inputWeightRef.setText(userCalled.weight.toString())
            inputAgeRef.setText(userCalled.age.toString())
            textWaterGoalRef.text = String.format("%.3f", userCalled.waterAmount) + " L/dia"
        }else {
            AlertDialog.Builder(this).setTitle(R.string.text_get_started).setMessage(R.string.text_get_started_description).show()
        }


        fun goToSettingsPage() {
            var homeScreen = Intent(this, MainActivity::class.java)
            startActivity(homeScreen)
        }

        fun deleteAllFields() {
//            call DAO here and remove from Room database too
            inputNameRef.text.clear()
            inputWeightRef.text.clear()
            inputAgeRef.text.clear()
            textWaterGoalRef.text = "---"
        }

        fun saveUserInfo(): Boolean {
            if (inputNameRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_name, Toast.LENGTH_SHORT).show()
                return false
            } else if (inputWeightRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_weight, Toast.LENGTH_SHORT).show()
                return false
            } else if (inputAgeRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_age, Toast.LENGTH_SHORT).show()
                return false
            } else {
                val name = inputNameRef.text.toString()
                val weightParsed = inputWeightRef.text.toString().toDouble()
                val ageParsed = inputAgeRef.text.toString().toInt()
                val calculateHowMuchWater = CalculateHowMuchWater()
                val waterAmount = calculateHowMuchWater.calculate(weightParsed, ageParsed).toFloat()
                textWaterGoalRef.text = String.format("%.3f", waterAmount) + " L/dia"
//              implements db.logic
                db.userDao.insert(
                    UserEntity(
                        name = name,
                        email = "exemplo@exemplo.com.br",
                        weight = weightParsed.toFloat(),
                        age = ageParsed,
                        waterAmount = waterAmount,
                        waterAmountDrank = 0F
                    )
                )
                return true
            }
        }

        fun updateUserInfo() : Boolean {
            if (inputNameRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_name, Toast.LENGTH_SHORT).show()
                return false
            } else if (inputWeightRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_weight, Toast.LENGTH_SHORT).show()
                return false
            } else if (inputAgeRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_age, Toast.LENGTH_SHORT).show()
                return false
            } else {
                val name = inputNameRef.text.toString()
                val weightParsed = inputWeightRef.text.toString().toDouble()
                val ageParsed = inputAgeRef.text.toString().toInt()
                val calculateHowMuchWater = CalculateHowMuchWater()
                val waterAmount = calculateHowMuchWater.calculate(weightParsed, ageParsed).toFloat()
                textWaterGoalRef.text = String.format("%.3f", waterAmount) + " L/dia"
//              implements db.logic
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = name,
                        email = "exemplo@exemplo.com.br",
                        weight = weightParsed.toFloat(),
                        age = ageParsed,
                        waterAmount = waterAmount,
                        waterAmountDrank = 0F
                    )
                )
                return true
            }
        }
//

        buttonCalculateHowMuchWaterRef.setOnClickListener {
            if (!hasUser) {
                val validation = saveUserInfo()
                if(validation) {
                    Toast.makeText(this, R.string.toast_message_saved, Toast.LENGTH_SHORT).show()
                    goToSettingsPage()
                }
            } else {
                val validation = updateUserInfo()
                if(validation) {
                    Toast.makeText(this, R.string.toast_message_update, Toast.LENGTH_SHORT).show()
                    goToSettingsPage()
                }
            }


        }
        buttonDeletion.setOnClickListener {
            deleteAllFields()
            Toast.makeText(this, R.string.toast_message_clear, Toast.LENGTH_SHORT).show()
        }

        buttonBackRef.setOnClickListener {
            goToSettingsPage()
        }
    }


}