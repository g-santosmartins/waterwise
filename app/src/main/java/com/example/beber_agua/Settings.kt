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
import com.example.beber_agua.utils.CalculateMetabolicDailyNeed

class Settings : AppCompatActivity() {

    private lateinit var buttonBackRef: ImageView
    private lateinit var buttonDeletion: ImageView
    private lateinit var buttonCalculateHowMuchWaterRef: AppCompatButton
    private lateinit var inputNameRef: EditText
    private lateinit var inputWeightRef: EditText
    private lateinit var inputHeightRef: EditText
    private lateinit var inputAgeRef: EditText
    private lateinit var textWaterGoalRef: TextView
    private lateinit var textCaloriesGoalRef: TextView

    private fun instanceAllFields() {
        buttonBackRef = findViewById(R.id.imageViewBackIcon)
        buttonCalculateHowMuchWaterRef = findViewById(R.id.buttonCalculateWaterAmount)
        buttonDeletion = findViewById(R.id.imageViewDeleteIcon)
        inputNameRef = findViewById(R.id.inputName)
        inputHeightRef = findViewById(R.id.inputHeight)
        inputWeightRef = findViewById(R.id.inputWeight)
        inputAgeRef = findViewById(R.id.inputAge)
        textWaterGoalRef = findViewById(R.id.textWaterGoal)
        textCaloriesGoalRef = findViewById(R.id.textCaloriesGoal)
    }

    private fun goToSettingsPage() {
        var homeScreen = Intent(this, MainActivity::class.java)
        startActivity(homeScreen)
    }

    private fun instanceDatabase(): AppDatabase {
        return Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "laagua.db"
        ).allowMainThreadQueries().build()
    }

    private fun validatInputs(): Boolean {
        if (inputNameRef.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_message_error_name, Toast.LENGTH_SHORT).show()
            return false
        } else if (inputWeightRef.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_message_error_weight, Toast.LENGTH_SHORT).show()
            return false
        } else if (inputAgeRef.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_message_error_age, Toast.LENGTH_SHORT).show()
            return false
        }else if (inputHeightRef.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_message_error_height, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun searchForUserData(): Boolean {
        val db = instanceDatabase()

        val userCalled: UserEntity = db.userDao.getById(1)
        var hasUser: Boolean = true
        if (userCalled != null) {
            val caloriesConverted =  userCalled.caloriesDailyAmount.toFloat()
            inputNameRef.setText(userCalled.name)
            inputWeightRef.setText(userCalled.weight.toInt().toString())
            inputAgeRef.setText(userCalled.age.toString())
            inputHeightRef.setText(userCalled.height.toInt().toString())
            textWaterGoalRef.text = String.format("%.3f", userCalled.waterAmount) + " L/dia"
            textCaloriesGoalRef.text =
                String.format("%.0f", caloriesConverted) + " Kcal/dia"
            return hasUser

        } else {
            hasUser = false
            AlertDialog.Builder(this).setTitle(R.string.text_get_started)
                .setMessage(R.string.text_get_started_description).show()
            return hasUser
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.hide()

        instanceAllFields()
//        catching a db instance
        val db = instanceDatabase()

        val hasUser = searchForUserData()

        fun deleteAllFields() {
//            call DAO here and remove from Room database too
            inputNameRef.text.clear()
            inputWeightRef.text.clear()
            inputAgeRef.text.clear()
            textWaterGoalRef.text = "---"
        }

        fun saveUserInfo(): Boolean {
            if ( validatInputs()) {
                val name = inputNameRef.text.toString()
                val weightParsed = inputWeightRef.text.toString().toDouble()
                val heightParsed = inputHeightRef.text.toString().toDouble()
                val ageParsed = inputAgeRef.text.toString().toInt()
                val calculateHowMuchWater = CalculateHowMuchWater()
                val calculateMetabolicDailyNeed = CalculateMetabolicDailyNeed.generateCaloricNeedResult(1, weightParsed, ageParsed, heightParsed, "very_low" )
                val waterAmount = calculateHowMuchWater.calculate(weightParsed, ageParsed).toFloat()
                textWaterGoalRef.text = String.format("%.3f", waterAmount) + " L/dia"
                println(calculateMetabolicDailyNeed)
//              implements db.logic
                db.userDao.insert(
                    UserEntity(
                        name = name,
                        email = "exemplo@exemplo.com.br",
                        weight = weightParsed.toFloat(),
                        height = heightParsed,
                        age = ageParsed,
                        waterAmount = waterAmount,
                        waterAmountDrank = 0F,
                        caloriesDailyAmount = calculateMetabolicDailyNeed
                    )
                )
                return true
            } else {
                return false
            }
        }

        fun updateUserInfo(): Boolean {
           if(validatInputs()){
                val name = inputNameRef.text.toString()
                val weightParsed = inputWeightRef.text.toString().toDouble()
               val heightParsed = inputHeightRef.text.toString().toDouble()
               val ageParsed = inputAgeRef.text.toString().toInt()
                val calculateHowMuchWater = CalculateHowMuchWater()
               val calculateMetabolicDailyNeed = CalculateMetabolicDailyNeed.generateCaloricNeedResult(1, weightParsed, ageParsed, heightParsed, "very_low" )
               val waterAmount = calculateHowMuchWater.calculate(weightParsed, ageParsed).toFloat()
                textWaterGoalRef.text = String.format("%.3f", waterAmount) + " L/dia"
//               textCaloriesGoalRef.text = String.format("%0.f", waterAmount) + " Kcal/dia"
//              implements db.logic
                db.userDao.update(
                    UserEntity(
                        id = 1,
                        name = name,
                        email = "exemplo@exemplo.com.br",
                        weight = weightParsed.toFloat(),
                        height = heightParsed,
                        age = ageParsed,
                        waterAmount = waterAmount,
                        waterAmountDrank = 0F,
                        caloriesDailyAmount = calculateMetabolicDailyNeed
                    )
                )
                return true
            } else {
                return false
           }
        }
//

        buttonCalculateHowMuchWaterRef.setOnClickListener {
            if (!hasUser) {
                val validation = saveUserInfo()
                if (validation) {
                    Toast.makeText(this, R.string.toast_message_saved, Toast.LENGTH_SHORT).show()
                    goToSettingsPage()
                }
            } else {
                val validation = updateUserInfo()
                if (validation) {
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

    override fun onResume() {
        super.onResume()
//        calling user validation on resume
        searchForUserData()
    }

}