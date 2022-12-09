package com.example.beber_agua.utils

class CalculateHowMuchWater {

    fun calculate(weight: Double, age: Int): Double {
        return when (age) {
            in 0..17 -> (weight * 40 / 100 / 10)
            in 18..55 -> (weight * 35 / 100 / 10)
            in 56..65 -> (weight * 30 / 100 / 10)
            in 66..150 -> (weight * 25 / 100 / 10)
            else -> {
                0.0
            }
        }
    }
}