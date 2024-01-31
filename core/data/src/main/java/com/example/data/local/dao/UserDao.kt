package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entities.OrderSelection
import com.example.data.entities.UserTest

@Dao
interface UserDao {
    @Query("select * from UserTest")
    fun getAll(): List<UserTest>

    @Insert
    fun insertMohammed(vararg user: UserTest)

//    TODO: add functions to insert and get order data from database
}

