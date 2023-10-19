package com.example.data.di

import com.example.data.repositories.AuthRepository
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.util.ValidationUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(Firebase.auth)
    }
}