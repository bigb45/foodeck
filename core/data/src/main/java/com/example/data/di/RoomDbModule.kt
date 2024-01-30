package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {
    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java, "local-database"
        ).build()
    }
}