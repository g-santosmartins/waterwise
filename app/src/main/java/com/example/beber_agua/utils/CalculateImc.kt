package com.example.beber_agua.utils

class CalculateImc {
    companion object {
        fun getMessageImcStatus(weight: Float, height: Float): String {
            val imcNumberResult: Float = calculateImcResult(weight, height)
            val finalResult = when (imcNumberResult.toString().toInt()) {
                in 0..16 -> "Magreza grave"
                in 16..17 -> "Magreza moderada"
                in 17..18 -> "Magreza leve"
                in 18..25 -> "Saudável"
                in 25..30 -> "Sobrepeso"
                in 30..35 -> "Obesidade  "
                in 35..40 -> "Obeseidade 2 "
                in 40..9999 -> "Obesidade 3"
                else -> "IMC indefinido"
            }
            return finalResult
        }

        private fun calculateImcResult(weight: Float, height: Float): Float {
            return (weight / (height * height))
        }
    }
}