package com.example.data.di

import android.content.Context
import com.example.common.Constants.baseUrl
import com.example.common.Constants.usersEndpoint
import com.example.data.api_services.UserApiService
import com.example.data.repositories.UsersRepository
import com.example.data.repositories.UsersRepositoryImpl
import com.example.data.util.PreferencesManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UsersModule {
    @Provides
    @Singleton
    fun provideUsersApiService(): UserApiService {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl + usersEndpoint).build().create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        usersService: UserApiService,
        sharedPrefs: PreferencesManager,
    ): UsersRepository {
        return UsersRepositoryImpl(usersService, sharedPrefs)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }
}