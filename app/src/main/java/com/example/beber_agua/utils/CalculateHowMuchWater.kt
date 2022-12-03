package com.example.beber_agua.utils

class CalculateHowMuchWater {

    fun calculate(weight: Double, age: Int): Double {

        var result: Double
        when (age) {
            in 0..17 -> result = (weight * 40 / 100 / 10)
            in 18..55 -> result = (weight * 35 / 100 / 10)
            in 56..65 -> result = (weight * 30 / 100 / 10)
            in 66..150 -> result = (weight * 25 / 100 / 10)
            else -> {
                result = 0.0
            }
        }
        return result
    }
}