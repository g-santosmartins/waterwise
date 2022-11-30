package com.example.laagua.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laagua.db.data.dao.UserDao
import com.example.laagua.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao : UserDao
}