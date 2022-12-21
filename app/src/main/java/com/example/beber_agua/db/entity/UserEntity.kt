package com.example.beber_agua.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val age: Int,
    val weight: Float,
    val height: Double,
    val waterAmount: Float,
    val waterAmountDrank: Float,
    val caloriesDailyAmount: Double,
    val dayOfYear: Int,
    val dailyGoalCompleted: Boolean
)
