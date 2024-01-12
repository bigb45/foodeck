package com.example.data.di

import com.example.common.Constants.authEndpoint
import com.example.common.Constants.baseUrl
import com.example.data.api_services.AuthApiService
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.AuthRepositoryImplCustomApi
import com.example.data.util.AccessTokenLocalDataSource
import com.example.data.util.PreferencesManager
import com.example.data.util.ValidationUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    @Named("firebase")
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(Firebase.auth)
    }

    @Provides
    @Singleton
    @Named("customApi")
    fun provideCustomApiAuthRepository(authService: AuthApiService, tokenStorageManager: PreferencesManager): AuthRepository {
        return AuthRepositoryImplCustomApi(authService, AccessTokenLocalDataSource(
            tokenStorageManager
        ))
    }

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl + authEndpoint).build().create(AuthApiService::class.java)
    }

}