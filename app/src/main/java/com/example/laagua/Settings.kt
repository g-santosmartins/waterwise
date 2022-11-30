package com.example.laagua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.beber_agua.R
import com.example.laagua.utils.CalculateHowMuchWater

class Settings : AppCompatActivity() {

    private lateinit var buttonBackRef : ImageView
    private lateinit var buttonDeletion : ImageView
    private lateinit var buttonCalculateHowMuchWaterRef: AppCompatButton
    private lateinit var inputNameRef: EditText
    private lateinit var inputWeightRef: EditText
    private lateinit var inputAgeRef: EditText
    private lateinit var textWaterGoalRef: TextView

//    calling the amout of water estimative class
    private lateinit var calculateHowMuchWater: CalculateHowMuchWater

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

        fun goToSettingsPage() {
            var homeScreen = Intent(this, MainActivity::class.java)
            startActivity(homeScreen)
        }
        fun deleteAllFields() {
//            call DAO here and remove from Room database too
            inputNameRef.text.clear()
            inputWeightRef.text.clear()
            inputAgeRef.text.clear()
        }

        buttonCalculateHowMuchWaterRef.setOnClickListener{
            if(inputNameRef.text.toString().isEmpty()){
                println("entando aqui")
                Toast.makeText(this, R.string.toast_message_error_name, Toast.LENGTH_SHORT).show()
            }else if (inputWeightRef.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_message_error_weight, Toast.LENGTH_SHORT).show()
            } else if(inputAgeRef.text.toString().isEmpty() ) {
                Toast.makeText(this, R.string.toast_message_error_age, Toast.LENGTH_SHORT).show()
            }else {
                println("entrando no calculo de ML")
                val weight = inputWeightRef.text.toString().toDouble()
                val age = inputAgeRef.text.toString().toInt()
                val calculateHowMuchWater = CalculateHowMuchWater()
               val result =  calculateHowMuchWater.calculate(weight, age)
                val parsedResult = result.toFloat()
                textWaterGoalRef.text = String.format("%.3f", parsedResult) + " L/dia"
                
            }
        }
        buttonDeletion.setOnClickListener{
            deleteAllFields()
            Toast.makeText(this, R.string.toast_message_clear, Toast.LENGTH_SHORT).show()
        }

        buttonBackRef.setOnClickListener{
            goToSettingsPage()
        }
    }
}