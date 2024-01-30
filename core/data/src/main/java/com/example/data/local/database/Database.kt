package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.entities.UserTest
import com.example.data.local.dao.UserDao

@TypeConverters(value = [Converters::class])
@Database(entities  = [UserTest::class], version = 1)
abstract class Database: RoomDatabase(){
    abstract fun userDao(): UserDao

}