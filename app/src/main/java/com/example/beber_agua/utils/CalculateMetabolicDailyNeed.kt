package com.example.beber_agua.utils

class CalculateMetabolicDailyNeed {
    companion object {
//        Fórmula para homens: TMB = fator da taxa de atividade x {66 + [(13,7 x Peso(kg)) + ( 5 x Altura(cm)) – (6,8 x Idade(anos))]}
//        Fórmula para mulheres: TMB = fator da taxa de atividade x {655 + [(9,6 x Peso(kg)) + (1,8 x Altura(cm)) – (4,7 x Idade(anos))]}
        fun generateCaloricNeedResult(gender: Int, weight: Double, age: Int, height: Double, fact: String): Double {
            val factorDictionary =
                mapOf(
                    "very_low" to 1.2,
                    "low" to 1.375,
                    "medium" to 1.55,
                    "high" to 1.725,
                    "extreme" to 1.9
                )
            return if (gender == 1) {
                factorDictionary[fact]!! * ((66 + (13.7 * weight)) + (5 * height) - (6.8 * age))
            } else {
                factorDictionary[fact]!! * (655 + (9.6 * weight) + (1.8 * height) - (4.7 * age))
            }
        }
    }

}
