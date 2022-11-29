package com.example.beber_agua

class CalculateHowMuchWater {

    fun calculate(weight: Double, age: Int): String {

        var result: String = ""
        when (age) {
            in 0..17 -> result = (weight * 40 / 100 / 10).toString()
            in 18..55 -> result = (weight * 35 / 100 / 10).toString()
            in 56..65 -> result = (weight * 30 / 100 / 10).toString()
            in 66..150 -> result = (weight * 25 / 100 / 10).toString()
            else -> {
                result = "Ops, houve um erro ao calcular a quantidade"
            }
        }
        return result
    }
}