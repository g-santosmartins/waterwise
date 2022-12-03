package com.example.beber_agua.db.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.beber_agua.db.entity.UserEntity

@Dao
interface UserDao {
//  Crud basic operations interface
    @Insert
     fun insert(user: UserEntity):Long

    @Update
     fun update(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
     fun getById(id: Long) : UserEntity

    @Query("DELETE FROM user WHERE id = :id")
     fun delete(id: Long)

    @Query("DELETE FROM user")
     fun deleteAll()

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<UserEntity>>
}