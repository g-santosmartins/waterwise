package com.example.beber_agua.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "glass")
data class GlassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val amountOfWater: Float,
)


