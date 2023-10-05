package com.example.fooddelivery.di

import com.example.fooddelivery.util.ValidationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideValidationUtil(): ValidationUtil {
        return ValidationUtil()
    }
}