package com.example.laagua.db.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.laagua.db.entity.User

@Dao
interface UserDao {
//  Crud basic operations interface
    @Insert
    suspend fun insert(user: User):Long

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>
}