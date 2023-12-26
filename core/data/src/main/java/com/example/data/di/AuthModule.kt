package com.example.data.di

import android.content.Context
import com.example.data.api_services.AuthApiService
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.AuthRepositoryImplCustomApi
import com.example.data.util.PreferencesManager
import com.example.data.util.ValidationUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


const val baseUrl = "http://192.168.1.104:4000/"

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
    @Named("firebase")
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(Firebase.auth)
    }

    @Provides
    @Singleton
    @Named("customApi")
    fun provideCustomApiAuthRepository(authService: AuthApiService): AuthRepository {
        return AuthRepositoryImplCustomApi(authService)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).build().create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager{
        return PreferencesManager(context)
    }
}