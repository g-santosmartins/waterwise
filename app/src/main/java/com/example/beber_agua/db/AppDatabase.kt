package com.example.beber_agua.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beber_agua.db.data.dao.UserDao
import com.example.beber_agua.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao : UserDao
}