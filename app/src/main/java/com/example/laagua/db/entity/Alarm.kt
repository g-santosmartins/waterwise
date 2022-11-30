package com.example.laagua.db.entity

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class Alarm(
    @PrimaryKey val uuid: String,
    var name: String,
    @ColumnInfo(name = "user_age")
    var descrition: String,
    @Embedded(prefix = "user_")
    val phone: ContactsContract.CommonDataKinds.Phone
)