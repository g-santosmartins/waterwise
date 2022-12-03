package com.example.beber_agua.db.entity

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp


@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val active: Boolean?,
    val deleted: Boolean?,
    val status: Int?,
    val timestamp: Timestamp ,
)